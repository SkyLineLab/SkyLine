/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.event

import android.app.Notification
import android.app.PendingIntent
import android.app.Person
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.notification.StatusBarNotification
import com.oracle.truffle.api.interop.TruffleObject
import com.oracle.truffle.js.runtime.objects.JSObject
import com.orhanobut.logger.Logger
import com.skyline.msgbot.script.api.util.ProfileImage
import com.skyline.msgbot.util.AppUtil
import org.graalvm.polyglot.Value
import org.graalvm.polyglot.proxy.ProxyObject

class BotChannel(
    private val bundle: Bundle,
    private val context: Context,
    private val session: Notification.Action,
    private val statusBarNotification: StatusBarNotification
) : ChatChannel {
    override val name: String
        get() {
            return if (AppUtil.getPackageVersion(statusBarNotification.packageName) >= 9.7 && Build.VERSION.SDK_INT >= 29) {
                bundle.getString("android.subText") ?: (bundle.getString("android.title") ?: "")
            } else {
                bundle.getString("android.summaryText") ?: (bundle.getString("android.title") ?: "")
            }
        }

    override val isGroupChat: Boolean
        get() {
            return if (AppUtil.getPackageVersion(statusBarNotification.packageName) >= 9.7 && Build.VERSION.SDK_INT >= 29) {
                bundle.getBoolean("android.isGroupConversation")
            } else {
                name == bundle.getString("android.summaryText")
            }
        }

    override fun send(message: Value?): Boolean {
        val sendIntent = Intent()
        val msg = Bundle()
        val actualInputs: ArrayList<RemoteInput> = java.util.ArrayList<RemoteInput>()
        try {
            for (inputable in session.remoteInputs!!) {
                msg.putCharSequence(inputable.resultKey, message?.toString())
                val builder = RemoteInput.Builder(inputable.resultKey)
                builder.setLabel(inputable.label)
                builder.setChoices(inputable.choices)
                builder.setAllowFreeFormInput(inputable.allowFreeFormInput)
                builder.addExtras(inputable.extras)
                actualInputs.add(builder.build())
            }
        } catch (e: Exception) {
            Logger.e("Send Error: ${e.message} stackTrace = ${e.stackTraceToString()}")
        }
        RemoteInput.addResultsToIntent(actualInputs.toArray(arrayOfNulls(actualInputs.size)), sendIntent, msg)
        return try {
            session.actionIntent.send(context, 0, sendIntent)
            true
        } catch (e: PendingIntent.CanceledException) {
            false
        }
    }

    override fun markAsRead(): Boolean {
        return try {
            statusBarNotification.notification.actions[0].actionIntent.send(context, 1, Intent())
            true
        }
        catch (e: PendingIntent.CanceledException){
            false
        }
    }

    override fun toString(): String {
        return "BotChannel[ name = $name isGroupChat = $isGroupChat ]"
    }
}

class BotSender(
    private val bundle: Bundle,
    private val profileImage: ProfileImage,
    private val packageName: String
) : ChatSender {
    override val name: String
        get() {
            return if (AppUtil.getPackageVersion(packageName) >= 9.7 && Build.VERSION.SDK_INT >= 29) {
                (bundle.get("android.messagingUser") as Person).name.toString()
            } else {
                bundle.get("android.title").toString()
            }
        }

    override val profileBase64: String
        get() = profileImage.hashCode

    override val profileHash: Int
        get() = profileImage.getProfileHash()

    override fun toString(): String {
        return "BotSender[ name = $name hash = $profileHash ]" // base64는 아주 길기에 생략
    }
}

data class MessageEvent(
    val message: String,
    val sender: ChatSender,
    val room: ChatChannel,
    val packageName: String,
    val chat: Bundle?
)

interface ChatChannel {
    val name: String

    val isGroupChat: Boolean

    fun send(message: Value?): Boolean

    fun markAsRead(): Boolean
}

interface ChatSender {
    val name: String

    val profileBase64: String

    val profileHash: Int
}