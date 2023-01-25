package com.skyline.msgbot.reflow.session

import com.skyline.msgbot.reflow.event.message.MessageChannel
import com.skyline.msgbot.reflow.event.message.MessageEvent

class ChannelList {

    fun get(channelId: Long): MessageChannel? {
        return ChannelSession.getSession(channelId)
    }

}