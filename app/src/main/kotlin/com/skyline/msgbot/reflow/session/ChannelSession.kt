package com.skyline.msgbot.reflow.session

import com.skyline.msgbot.reflow.event.message.MessageChannel

object ChannelSession {

    private val sessions: HashMap<Long, MessageChannel> = HashMap() // roomName, MessageEvent

    fun addSession(eventData: MessageChannel) {
        sessions[eventData.channelId] = eventData
    }

    fun getSession(room: Long): MessageChannel? {
        return if (sessions[room] === null) {
            null
        } else {
            sessions[room]
        }
    }

    fun hasSession(room: Long): Boolean {
        return sessions[room] != null
    }

}