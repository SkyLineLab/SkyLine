/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.session

import com.skyline.msgbot.bot.event.MessageEvent

/**
 * This class saves and loads sessions for each channel.
 *
 * @author naijun
 */
object BotChannelSession {
    private val sessions: HashMap<String, MessageEvent> = HashMap() // roomName, MessageEvent

    fun addSession(eventData: MessageEvent) {
        sessions[eventData.room.name] = eventData;
    }

    fun getSession(room: String): MessageEvent? {
        return if (sessions[room] === null) {
            null;
        } else {
            sessions[room]
        }
    }
}