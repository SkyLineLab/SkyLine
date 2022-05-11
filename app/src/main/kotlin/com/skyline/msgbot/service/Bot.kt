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