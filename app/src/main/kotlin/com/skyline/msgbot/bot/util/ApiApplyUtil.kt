/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.util

import com.skyline.msgbot.bot.api.Api
import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.bot.project.BotProject
import com.skyline.msgbot.nodejs.modules.TimersBuiltinsModule
import org.graalvm.polyglot.Value
import java.util.function.Function

object ApiApplyUtil {
    fun applyBotApi(value: Value, isProject: Boolean, runtimeId: Number) {
        value.putMember("FileStream", FileStream)
        value.putMember("Api", Api)
        if (isProject) {
            value.putMember("BotProject", BotProject(runtimeId))
        }

        // nodejs: Timer
        value.putMember("timers", TimersBuiltinsModule)
        value.putMember("setTimeout", TimersBuiltinsModule.Companion.SetTimeOut())
        value.putMember("clearTimeout", Function<Any, Any> { stackId ->
            TimersBuiltinsModule.clearTimeout(stackId as Number)
        })
        value.putMember("setInterval", TimersBuiltinsModule.Companion.SetInterval())
        value.putMember("clearInterval", Function<Any, Any> { stackId ->
            TimersBuiltinsModule.clearInterval(stackId as Number)
        })
        value.putMember("setImmediate", TimersBuiltinsModule.Companion.SetImmediate())
        value.putMember("clearImmediate", Function<Any, Any> { stackId ->
            TimersBuiltinsModule.clearInterval(stackId as Number)
        })
    }
}