/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.nodejs.utils

import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.bot.util.SDCardUtils
import com.skyline.msgbot.setting.Constants
import java.io.File

/**
 * NodeJS Module Init Utils
 *
 * @author naijun
 */
object NodeJSModuleInitUtils {
    fun initAllModule() {
        initTimer()
    }

    private fun initTimer() {
        if (!checkModule("timers")) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("node_modules").resolve("timers").mkdirs()
            FileStream.write("node_modules/timers/index.js", NodeJSModuleScript.timers)
        }
    }

    private fun checkModule(module: String): Boolean {
        return try {
            val path = File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/node_modules/$module/index.js")
            path.exists()
        } catch (e: Exception) {
            false
        }
    }
}