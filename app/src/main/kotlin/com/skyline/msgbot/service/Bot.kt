/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.service

import android.os.Bundle
import com.orhanobut.logger.Logger
import com.skyline.msgbot.event.Events
import com.skyline.msgbot.event.MessageEvent
import com.skyline.msgbot.runtime.RuntimeManager
import com.skyline.msgbot.session.ChannelSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Bot 관련 기능을 담당
 * @author naijun
 */
object Bot {
    fun callMessage(event: MessageEvent) {
        ChannelSession.addSession(eventData = event)
        CoroutineScope(Dispatchers.IO).launch {
            val messageEvent = arrayOf(event)
            for (client in RuntimeManager.clients) {
                Logger.d("event = ${Events.ON_MESSAGE} key = ${client.key} value = ${client.value} power = ${RuntimeManager.powerMap[client.key]}")
                if (RuntimeManager.getIsPowerOn(client.key)) {
                    client.value.emit(Events.ON_MESSAGE, *messageEvent)
                }
            }
        }
    }

    fun callNotificationPosted(event: Bundle) {
        CoroutineScope(Dispatchers.IO).launch {
            val messageEvent = arrayOf(event)
            for (client in RuntimeManager.clients) {
                Logger.d("event = ${Events.ON_NOTIFICATION_POSTED} key = ${client.key} value = ${client.value} power = ${RuntimeManager.powerMap[client.key]}")
                if (RuntimeManager.getIsPowerOn(client.key)) {
                    client.value.emit(Events.ON_NOTIFICATION_POSTED, *messageEvent)
                }
            }
        }
    }

    /**
     * 구현은 되어있지만 키고 끌수있는 스위치가 없어서 작동못시킴
     * @feature
     */
    fun callPowerEvent(power: Boolean, time: Int, targetId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val event = arrayOf(time)
            val client = RuntimeManager.clients[targetId] ?: throw IllegalAccessError("Sorry, We cannot access to $targetId")
            if (power) {
                client.emit(Events.BOT_ON, event)
            } else {
                client.emit(Events.BOT_OFF, event)
            }
        }
    }
}