/*
 * MIT License
 *
 * Copyright (c) 2022 SkyLineLab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
import com.oracle.truffle.js.runtime.Errors
import com.orhanobut.logger.Logger
import com.skyline.msgbot.script.api.util.ProfileImage
import com.skyline.msgbot.session.ChannelSession
import com.skyline.msgbot.util.AppUtil
import com.skyline.msgbot.util.JSPromise
import org.graalvm.polyglot.Value

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

    override fun send(message: Value?): Value {
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
        Logger.d(msg)
        return try {
            session.actionIntent.send(context, 0, sendIntent)
            JSPromise.wrapPromise(result = true, isSuccess = true)
        } catch (e: PendingIntent.CanceledException) {
            e.printStackTrace()
            JSPromise.wrapPromise(result = false, isSuccess = false)
        }
    }

    override fun sendAllRoom(message: Value?): Value {
        val keys = ChannelSession.sessions.keys.toTypedArray()
        for (i in 0 until ChannelSession.sessions.size) {
            val roomName = keys[i]
            ChannelSession.getSession(roomName)!!.room.send(message)
        }
        return JSPromise.wrapPromise(result = true, isSuccess = true)
    }

    override fun markAsRead(): Value {
        return try {
            statusBarNotification.notification.actions[0].actionIntent.send(context, 1, Intent())
            JSPromise.wrapPromise(result = true, isSuccess = true)
        }
        catch (e: PendingIntent.CanceledException){
            JSPromise.wrapPromise(result = false, isSuccess = false)
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

    override val userHash: String
        get() {
            return if (AppUtil.getPackageVersion(packageName) >= 9.7 && Build.VERSION.SDK_INT >= 30) {
                (bundle.get("android.messagingUser") as Person).key.toString()
            } else {
                throw Errors.createError("User Hash is Add to SDK_LEVEL 30 or kakaotalk version over than 9.7.0")
            }
        }

    override fun toString(): String {
        return "BotSender[ name = $name hash = $profileHash ]" // base64는 아주 길기에 생략
    }

}

class MessageEvent(
    val message: String,
    val sender: ChatSender,
    val room: ChatChannel,
    val packageName: String,
    val chat: Bundle?
) : Event {

    override val isTrusted: Boolean
        get() = true

    override val type: String
        get() = Events.ON_MESSAGE

    override val timeStamp: Long
        get() = System.currentTimeMillis()

    override val defaultPrevented: Boolean
        get() = false

    override val scope: String
        get() = "global"

}

interface ChatChannel {

    val name: String

    val isGroupChat: Boolean

    fun send(message: Value?): Value

    fun sendAllRoom(message: Value?): Value

    fun markAsRead(): Value

}

interface ChatSender {

    val name: String

    val profileBase64: String

    val profileHash: Int

    val userHash: String

}