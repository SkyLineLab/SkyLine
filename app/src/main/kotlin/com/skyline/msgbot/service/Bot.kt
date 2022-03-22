/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.service

import android.os.Bundle
import com.orhanobut.logger.Logger
import com.skyline.msgbot.event.MessageEvent
import com.skyline.msgbot.runtime.RuntimeManager
import com.skyline.msgbot.session.ChannelSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Bot {
    fun callMessage(event: MessageEvent) {
        ChannelSession.addSession(eventData = event)
        CoroutineScope(Dispatchers.IO).launch {
            val messageEvent = arrayOf(event)
            for (client in RuntimeManager.clients) {
                Logger.d("event = message key = ${client.key} value = ${client.value} power = ${RuntimeManager.powerMap[client.key]}")
                if (RuntimeManager.getIsPowerOn(client.key)) {
                    client.value.emit("message", *messageEvent)
                }
            }
        }
    }

    fun callNotificationPosted(event: Bundle) {
        CoroutineScope(Dispatchers.IO).launch {
            val messageEvent = arrayOf(event)
            for (client in RuntimeManager.clients) {
                Logger.d("event = notificationPosted key = ${client.key} value = ${client.value} power = ${RuntimeManager.powerMap[client.key]}")
                if (RuntimeManager.getIsPowerOn(client.key)) {
                    client.value.emit("notificationPosted", *messageEvent)
                }
            }
        }
    }
}