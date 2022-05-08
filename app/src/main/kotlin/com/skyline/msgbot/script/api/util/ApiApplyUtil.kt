/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.script.api.util

import android.annotation.SuppressLint
import com.skyline.gordjs.GordJS
import com.skyline.msgbot.project.BotProject
import com.skyline.msgbot.script.api.Api
import com.skyline.msgbot.script.api.Bridge
import com.skyline.msgbot.script.api.Event
import com.skyline.msgbot.script.api.FileStream
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Value

object ApiApplyUtil {
    fun apply(value: Value, isProject: Boolean, runtimeId: Int) {
        value.putMember("FileStream", FileStream)
        value.putMember("Api", Api)
        value.putMember("Bridge", Bridge)
        value.putMember("Event", Event::class.java)

        if (isProject) {
            value.putMember("BotProject", BotProject(runtimeId))
        }

        GordJS.applyContext(value) // init nodejs
    }

    @SuppressLint("SdCardPath")
    fun installNodejs(context: Context) {
        val source = FileStream.read("/data/user/0/com.skyline.msgbot/files/global.js")
        context.eval("js", source)
    }
}