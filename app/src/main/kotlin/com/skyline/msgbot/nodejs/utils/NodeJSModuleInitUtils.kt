/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.nodejs.utils

import android.annotation.SuppressLint
import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.utils.SDCardUtils
import com.skyline.msgbot.setting.Constants
import com.skyline.msgbot.utils.HttpRequestUtil
import com.skyline.msgbot.utils.NpmUtil
import org.json.JSONObject
import java.io.File

/**
 * NodeJS Module Init Utils
 *
 * @author naijun
 */
object NodeJSModuleInitUtils {
    fun initAllModule(project: String) {
        initBuffer(project)
        initProcess(project)
        initTimer(project)
        initUtil(project)
        initEvents(project)
        initDomain(project)
        initGlobal(project)
    }

    private fun initTimer(project: String) {
        if (!checkModule("timers", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("timers").mkdirs()
            FileStream.write("Projects/$project/node_modules/timers/index.js", NodeJSModuleScript.timers)
        }
    }

    @SuppressLint("SdCardPath")
    private fun initGlobal(project: String) {
        if (!File("/data/user/0/com.skyline.msgbot/files/lib/$project/global.js").exists()) {
            if (!File("/data/user/0/com.skyline.msgbot/files/lib/$project").exists()) {
                File("/data/user/0").resolve("com.skyline.msgbot").resolve("files").resolve("lib").resolve(project).mkdirs()
            }

            FileStream.write("/data/user/0/com.skyline.msgbot/files/lib/$project/global.js", NodeJSModuleScript.getGlobalScript(project))
        }
    }

    private fun initBuffer(project: String) {
        if (!checkModuleDeep("buffer", project)) {
            Thread {
                NpmUtil.getPackage("buffer", project)
            }.start()
        }
    }

    private fun initProcess(project: String) {
        if (!checkModule("process", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("process").mkdirs()
            Thread {
                val process = HttpRequestUtil.request("https://archethic.github.io/nodejs/process.js")

                FileStream.write("Projects/$project/node_modules/process/index.js", process)
            }.start()
        }
    }

    private fun initUtil(project: String) {
        if (!checkModule("util", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("util").mkdirs()
            Thread {
                val process = HttpRequestUtil.request("https://archethic.github.io/nodejs/util.js")

                FileStream.write("Projects/$project/node_modules/util/index.js", process)
            }.start()
        }
    }

    private fun initEvents(project: String) {
        if (!checkModule("events", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("events").mkdirs()
            Thread {
                val process = HttpRequestUtil.request("https://archethic.github.io/nodejs/events.js")

                FileStream.write("Projects/$project/node_modules/events/index.js", process)
            }.start()
        }
    }

    private fun initDomain(project: String) {
        if (!checkModule("domain", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("domain").mkdirs()
            Thread {
                val process = HttpRequestUtil.request("https://archethic.github.io/nodejs/domain.js")

                FileStream.write("Projects/$project/node_modules/domain/index.js", process)
            }.start()
        }
    }

    private fun checkModule(module: String, project: String): Boolean {
        return try {
            val path = File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$project/node_modules/$module/index.js")
            path.exists()
        } catch (e: Exception) {
            false
        }
    }

    private fun checkModuleDeep(module: String, project: String): Boolean {
        return try {
            val path = File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$project/node_modules/$module/index.js")
            val packageJson = JSONObject(FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$project/node_modules/$module/index.js")!!)
            val exists = packageJson.getJSONObject("dependencies").has(module)
            path.exists() && exists
        } catch (e: Exception) {
            false
        }
    }
}