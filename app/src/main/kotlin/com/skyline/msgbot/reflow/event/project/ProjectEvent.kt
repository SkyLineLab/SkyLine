package com.skyline.msgbot.reflow.event.project

import com.skyline.msgbot.reflow.event.Event
import com.skyline.msgbot.reflow.event.EventBase
import com.skyline.msgbot.reflow.util.TrustUtil

class ProjectEvent : EventBase {

    override val isTrusted: Boolean = TrustUtil.getIsTrust()

    override val type: String
        get() = Event.ON_START_COMPILE

    override val timeStamp: Long = System.currentTimeMillis()

    override val defaultPrevented: Boolean
        get() = true

    override val scope: String
        get() = "scope"

}