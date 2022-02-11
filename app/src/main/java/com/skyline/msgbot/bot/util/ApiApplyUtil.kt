/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
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