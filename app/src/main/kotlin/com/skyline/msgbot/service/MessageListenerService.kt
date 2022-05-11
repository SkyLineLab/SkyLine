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

package com.skyline.msgbot.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.orhanobut.logger.Logger
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.event.BotChannel
import com.skyline.msgbot.event.BotSender
import com.skyline.msgbot.event.MessageEvent
import com.skyline.msgbot.script.api.util.ProfileImage
import com.skyline.msgbot.util.AppUtil
import java.util.*

class MessageListenerService : NotificationListenerService() {

    override fun onCreate() {
        super.onCreate()
        Logger.d("Notification Listener Create")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("Notification Listener Destroy")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        Logger.d("notificationPosted")
        if (sbn == null || CoreHelper.allowPackageNames.find { sbn.packageName.startsWith(it) }.isNullOrEmpty()) return
        try {
            if (!AppUtil.isNougatAbove()) {
                Logger.d("Nougat not above")
                val extender = Notification.WearableExtender(sbn.notification)
                for (action in extender.actions) {
                    if (action.remoteInputs?.isEmpty() == true || action.remoteInputs == null) return
                    if (
                        action.title.toString().lowercase(Locale.getDefault()).contains("reply") ||
                        action.title.toString().lowercase(Locale.getDefault()).contains("답장")
                    ) {
                        val data = sbn.notification.extras
                        Logger.d(data)
                        val message = data.get("android.text").toString()
                        val channel = BotChannel(data, applicationContext, action, sbn)
                        val profileImage = ProfileImage(applicationContext, sbn, data)
                        val sender = BotSender(
                            data,
                            profileImage,
                            sbn.packageName
                        )
                        val messageEvent = MessageEvent(
                            message,
                            sender,
                            channel,
                            sbn.packageName,
                            data
                        )

                        Bot.callMessage(messageEvent)
                        Bot.callNotificationPosted(data)
                    }
                }
            } else {
                Logger.d("Nougat above")

                if (sbn.notification != null) {
                    if (sbn.notification.actions == null) return
                    for (i in 0 until sbn.notification.actions.size) {
                        if (
                            sbn.notification.actions[i].title.toString().lowercase(Locale.getDefault()).contains("reply") ||
                            sbn.notification.actions[i].title.toString().lowercase(Locale.getDefault()).contains("답장")
                        ) {
                            val data = sbn.notification.extras
                            Logger.d("Data = $data")
                            if (data == null) {
                                Logger.w("Data is Null")
                                return
                            }
                            Logger.d("action Size = ${sbn.notification.actions.size}")
                            val message = data.get("android.text").toString()
                            val profileImage = ProfileImage(applicationContext, sbn, data)
                            val channel = BotChannel(data, applicationContext, sbn.notification.actions[i], sbn)
                            val sender = BotSender(
                                data,
                                profileImage,
                                sbn.packageName
                            )
                            val messageEvent = MessageEvent(
                                message,
                                sender,
                                channel,
                                sbn.packageName,
                                data
                            )

                            Bot.callMessage(messageEvent)
                            Bot.callNotificationPosted(data)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Logger.e("Notification Error: ${e.message} stackTrace = ${e.stackTraceToString()}")
        }
    }

}