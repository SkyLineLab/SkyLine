/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.event

import android.app.Notification
import android.app.PendingIntent
import android.app.Person
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.notification.StatusBarNotification
import com.oracle.truffle.api.utilities.JSONHelper
import com.oracle.truffle.js.builtins.JSONBuiltins
import com.oracle.truffle.js.runtime.builtins.JSON
import com.oracle.truffle.js.runtime.objects.JSObject
import com.oracle.truffle.js.runtime.objects.JSObjectUtil
import com.oracle.truffle.js.runtime.objects.Undefined
import com.skyline.msgbot.bot.api.ProfileImage
import com.skyline.msgbot.utils.AppUtil
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
        val sendData = try {
            message?.`as`(Map::class.java)?.toMap().toString().replace("{", "{ ").replace("}", " }").replace("=", ": ")
        } catch (e: Exception) {
            if (Undefined.instance.equals(message?.`as`(Any::class.java))) {
                 "undefined"
            } else {
                message?.`as`(Any::class.java).toString()
            }
        }
        for (inputable in session.remoteInputs)
            msg.putCharSequence(inputable.resultKey, sendData)
        RemoteInput.addResultsToIntent(session.remoteInputs, sendIntent, msg)
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