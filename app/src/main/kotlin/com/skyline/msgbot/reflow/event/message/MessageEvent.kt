package com.skyline.msgbot.reflow.event.message

import android.os.Bundle
import com.skyline.msgbot.reflow.event.Event
import com.skyline.msgbot.reflow.util.ToStringUtil
import com.skyline.msgbot.reflow.util.TrustUtil

data class MessageEvent(
    val message: String,
    val sender: MessageSender,
    val room: MessageChannel,
    val packageName: String,
    val chat: Bundle?
) : Event {

    override val isTrusted: Boolean = TrustUtil.getIsTrust()

    override val type: String
        get() = "message"

    override val timeStamp: Long = System.currentTimeMillis()

    override val defaultPrevented: Boolean
        get() = false

    override val scope: String
        get() = "global"

    override fun toString(): String {
        return ToStringUtil.toStringFormat(this)
    }

}