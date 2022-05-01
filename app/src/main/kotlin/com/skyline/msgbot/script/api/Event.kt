/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.script.api

import org.graalvm.polyglot.Value

class Event(
    val eventName: String,
    val data: Value
) {
    init {
        data.putMember("isTrusted", false)
        data.putMember("type", eventName)
        data.putMember("timeStamp", System.currentTimeMillis())
        if (!data.hasMember("defaultPrevented")) data.putMember("defaultPrevented", true)
        if (!data.hasMember("scope")) data.putMember("scope", "scope")
    }
}