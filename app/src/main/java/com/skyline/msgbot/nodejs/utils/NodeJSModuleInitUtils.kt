/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.nodejs.utils

import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.utils.SDCardUtils
import com.skyline.msgbot.setting.Constants
import com.skyline.msgbot.utils.HttpRequestUtil
import java.io.File

/**
 * NodeJS Module Init Utils
 *
 * @author naijun
 */
object NodeJSModuleInitUtils {
    fun initAllModule() {
        initTimer()
        initBuffer()
        initProcess()
        initGlobal()
    }

    private fun initTimer() {
        if (!checkModule("timers")) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("node_modules").resolve("timers").mkdirs()
            FileStream.write("node_modules/timers/index.js", NodeJSModuleScript.timers)
        }
    }

    private fun initGlobal() {
        if (!File("/data/user/0/com.skyline.msgbot/files/lib/global.js").exists()) {
            if (!File("/data/user/0/com.skyline.msgbot/files/lib").exists()) {
                File("/data/user/0").resolve("com.skyline.msgbot").resolve("files").resolve("lib").mkdirs()
            }

            FileStream.write("/data/user/0/com.skyline.msgbot/files/lib/global.js", NodeJSModuleScript.global)
        }
    }

    private fun initBuffer() {
        if (!checkModule("buffer")) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("node_modules").resolve("buffer").mkdirs()
            Thread {
                val base64 = HttpRequestUtil.request("https://raw.githubusercontent.com/beatgammit/base64-js/master/index.js")
                val ieee754 = HttpRequestUtil.request("https://raw.githubusercontent.com/feross/ieee754/master/index.js")
                val result = HttpRequestUtil.request("https://raw.githubusercontent.com/feross/buffer/master/index.js")
                    .replace("require('base64-js')", "require('./base64-js')")
                    .replace("require('ieee754')", "require('./ieee754')")

                FileStream.write("node_modules/buffer/base64-js.js", base64)
                FileStream.write("node_modules/buffer/ieee754.js", ieee754)
                FileStream.write("node_modules/buffer/index.js", result)
            }.start()
        }
    }

    private fun initProcess() {
        if (!checkModule("process")) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("node_modules").resolve("process").mkdirs()
            Thread {
                val process = HttpRequestUtil.request("https://archethic.github.io/nodejs/process.js")

                FileStream.write("node_modules/process/index.js", process)
            }.start()
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