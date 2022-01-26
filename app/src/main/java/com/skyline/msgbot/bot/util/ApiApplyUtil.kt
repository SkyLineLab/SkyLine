/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.util

import com.skyline.msgbot.bot.api.Api
import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.bot.project.BotProject
import org.graalvm.polyglot.Value

object ApiApplyUtil {
    fun applyBotApi(value: Value, isProject: Boolean, runtimeId: Number) {
        value.putMember("FileStream", FileStream)
        value.putMember("Api", Api)
        if (isProject) {
            value.putMember("BotProject", BotProject(runtimeId))
        }
    }
}