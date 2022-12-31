package com.skyline.msgbot.reflow.event.power

import com.skyline.msgbot.reflow.event.Event
import com.skyline.msgbot.reflow.event.EventBase
import com.skyline.msgbot.reflow.project.Project
import com.skyline.msgbot.reflow.util.TrustUtil
import org.graalvm.polyglot.Value
import org.graalvm.polyglot.proxy.ProxyObject

class PowerEvent(
    private val project: Project,
    private val botOn: Boolean,
    val reason: String,
    val target: String
) : EventBase {

    override val isTrusted: Boolean = TrustUtil.getIsTrust()

    override val type: String
        get() = if (botOn) {
            Event.BOT_ON
        } else Event.BOT_OFF

    override val timeStamp: Long = System.currentTimeMillis()

    override val defaultPrevented: Boolean
        get() = true

    override val scope: String
        get() = "scope"

    val detail = ProxyObject.fromMap(HashMap()).apply {
        putMember("line", null)
        putMember("name", Value.asValue(project.name))
    }

}