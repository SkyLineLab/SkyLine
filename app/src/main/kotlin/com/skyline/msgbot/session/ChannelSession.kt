/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.session

import com.skyline.msgbot.event.MessageEvent

object ChannelSession {
    private val sessions: HashMap<String, MessageEvent> = HashMap() // roomName, MessageEvent

    fun addSession(eventData: MessageEvent) {
        sessions[eventData.room.name] = eventData
    }

    fun getSession(room: String): MessageEvent? {
        return if (sessions[room] === null) {
            null
        } else {
            sessions[room]
        }
    }

    fun hasSession(room: String): Boolean {
        return sessions[room] != null
    }
}