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
        initHttp(project)
        initHttps(project)
        initUrl(project)
        initPunycode(project)
        initQueryString(project)
        initStream(project)
        initAssert(project)
        initZlib(project)
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

    private fun initHttp(project: String) {
        if (!checkModule("http", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("http").mkdirs()
            Thread {
                val http = HttpRequestUtil.request("https://archethic.github.io/nodejs/http.js")

                FileStream.write("Projects/$project/node_modules/http/index.js", http)
            }.start()
        }
    }

    private fun initHttps(project: String) {
        if (!checkModule("https", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("https").mkdirs()
            Thread {
                val https = HttpRequestUtil.request("https://archethic.github.io/nodejs/https.js")

                FileStream.write("Projects/$project/node_modules/https/index.js", https)
            }.start()
        }
    }

    private fun initUrl(project: String) {
        if (!checkModule("url", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("url").mkdirs()
            Thread {
                val url = HttpRequestUtil.request("https://archethic.github.io/nodejs/url.js")

                FileStream.write("Projects/$project/node_modules/url/index.js", url)
            }.start()
        }
    }

    private fun initPunycode(project: String) {
        if (!checkModule("punycode", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("punycode").mkdirs()
            Thread {
                val punycode = HttpRequestUtil.request("https://archethic.github.io/nodejs/punycode.js")

                FileStream.write("Projects/$project/node_modules/punycode/index.js", punycode)
            }.start()
        }
    }

    private fun initQueryString(project: String) {
        if (!checkModule("querystring", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("querystring").mkdirs()
            Thread {
                val qs = HttpRequestUtil.request("https://archethic.github.io/nodejs/querystring.js")

                FileStream.write("Projects/$project/node_modules/querystring/index.js", qs)
            }.start()
        }
    }

    private fun initAssert(project: String) {
        if (!checkModule("assert", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("assert").mkdirs()
            Thread {
                val assertScript = HttpRequestUtil.request("https://archethic.github.io/nodejs/assert.js")

                FileStream.write("Projects/$project/node_modules/assert/index.js", assertScript)
            }.start()
        }
    }

    private fun initZlib(project: String) {
        if (!checkModule("zlib", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("zlib").mkdirs()
            Thread {
                val zlibScript = HttpRequestUtil.request("https://archethic.github.io/nodejs/zlib.js")

                FileStream.write("Projects/$project/node_modules/zlib/index.js", zlibScript)
            }.start()
        }
    }

    private fun initStream(project: String) {
        if (!checkModule("stream", project)) {
            File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(project).resolve("node_modules").resolve("stream").mkdirs()
            Thread {
                val duplex = HttpRequestUtil.request("https://archethic.github.io/nodejs/stream_duplex.js")
                val passthrough = HttpRequestUtil.request("https://archethic.github.io/nodejs/stream_passthrough.js")
                val readable = HttpRequestUtil.request("https://archethic.github.io/nodejs/stream_readable.js")
                val transform = HttpRequestUtil.request("https://archethic.github.io/nodejs/stream_transform.js")
                val writable = HttpRequestUtil.request("https://archethic.github.io/nodejs/stream_writable.js")
                val index = HttpRequestUtil.request("https://archethic.github.io/nodejs/stream.js")

                FileStream.write("Projects/$project/node_modules/stream/stream_duplex.js", duplex)
                FileStream.write("Projects/$project/node_modules/stream/stream_passthrough.js", passthrough)
                FileStream.write("Projects/$project/node_modules/stream/stream_readable.js", readable)
                FileStream.write("Projects/$project/node_modules/stream/stream_transform.js", transform)
                FileStream.write("Projects/$project/node_modules/stream/stream_writable.js", writable)
                FileStream.write("Projects/$project/node_modules/stream/index.js", index)
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