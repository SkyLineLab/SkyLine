/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.widget.Toast
import com.skyline.msgbot.bot.Bot
import com.skyline.msgbot.bot.api.ProfileImage
import com.skyline.msgbot.bot.event.BotChannel
import com.skyline.msgbot.bot.event.ChatSender
import com.skyline.msgbot.bot.event.MessageEvent
import com.skyline.msgbot.setting.Constants
import java.util.*

class MessageListenerService : NotificationListenerService() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if(sbn === null || Constants.allowPackageNames.find { sbn.packageName.startsWith(it) }.isNullOrEmpty()) return
        try {
            val extender: Notification.WearableExtender = Notification.WearableExtender(sbn.notification)
            for (action in extender.actions) {
                if (action.remoteInputs.isEmpty()) return
                if (
                    action.title.toString().lowercase(Locale.getDefault()).contains("reply") ||
                    action.title.toString().lowercase(Locale.getDefault()).contains("답장")
                ) {
                    println("message")
                    val data = sbn.notification.extras
                    val message = data.get("android.text").toString()
                    val channel = BotChannel(data, applicationContext, action, sbn)
                    val profileImage = ProfileImage(applicationContext, sbn)
                    val sender = ChatSender(
                        data.get("android.title").toString(),
                        profileImage.hashCode,
                        profileImage.getProfileHash()
                    )
                    val eventData = MessageEvent(
                        message,
                        sender,
                        channel,
                        sbn.packageName
                    )
                    println("ready call message")
                    Bot.callOnMessage(eventData)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
        }
    }
}