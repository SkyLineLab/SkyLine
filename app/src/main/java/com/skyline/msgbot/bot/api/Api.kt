/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.api

import com.skyline.msgbot.bot.runtime.RuntimeManager
import com.skyline.msgbot.bot.util.ApiApplyUtil
import com.skyline.msgbot.bot.util.ContextUtils
import com.skyline.msgbot.bot.util.SDCardUtils
import com.skyline.msgbot.setting.Constants
import org.graalvm.polyglot.Source

object Api {
    fun compile(): Boolean {
        try {
            RuntimeManager.runtimes.forEach { key, value ->
                run {
                    val cx = ContextUtils.getJSContext()
                    ApiApplyUtil.applyBotApi(cx.getBindings("js"), true, key)
                    RuntimeManager.runtimes[key] = cx
                    RuntimeManager.runtimes[key]?.eval(
                        Source.create(
                            "js",
                            FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/${RuntimeManager.projectIds[key]}/script.js")
                        )
                    )
                }
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}