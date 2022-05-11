/*
 * MIT License
 *
 * Copyright (c) 2022 SkyLineLab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.skyline.msgbot.project

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.orhanobut.logger.Logger
import com.skyline.gordjs.util.NodeModuleUtil
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.script.ScriptLanguage
import com.skyline.msgbot.script.api.FileStream
import org.apache.commons.io.IOUtils
import java.io.File

object ProjectInitUtil {

    /**
     * 코루틴 내부에서 사용하길 바랍니다.
     * @see com.skyline.msgbot.runtime.RuntimeManager
     */
    fun createProject(projectName: String, language: ScriptLanguage): Boolean {
        if (isExistsProject(projectName)) return false
        return try {
            val gnm = File("${CoreHelper.filesDir}/global.js")
            if (!gnm.exists()) {
                val inputStream = CoreHelper.contextGetter!!.invoke().assets.open("global.js")
                val source = IOUtils.toString(inputStream)
                FileStream.write("${CoreHelper.filesDir}/global.js", source)
            }

            val nmPath = File("${CoreHelper.filesDir}/node_modules")
            if (!nmPath.exists()) {
                Logger.d("Noting :(")
                NodeModuleUtil.installNodeModule()
            }

            val appPath = File(CoreHelper.sdcardPath).resolve(CoreHelper.directoryName).resolve("Projects")
            if (!appPath.exists()) {
                appPath.mkdirs()
            }

            val dirPath = File("${appPath.absolutePath}/$projectName")
            if (!dirPath.exists()) {
                dirPath.mkdir()
            }

            val packageJsonPath = "${appPath.absolutePath}/$projectName/package.json"
            val packageJson = File(packageJsonPath)
            if (!packageJson.exists()) {
                val jsonObject = JsonObject()
                jsonObject.addProperty("name", projectName)
                jsonObject.addProperty("version", "1.0.0")
                jsonObject.add("dependencies", JsonObject())
                FileStream.write(packageJsonPath, jsonObject.toString())
            }

            when (language) {
                ScriptLanguage.JAVASCRIPT -> {
                    Logger.d("you chose JavaScript")
                    val inputStream = CoreHelper.contextGetter!!.invoke().assets.open("jsInitScript.js")
                    val source = IOUtils.toString(inputStream)
                    FileStream.write("${dirPath.absolutePath}/script.js", source)
                }

                else -> throw UnsupportedOperationException("Not Support Language: $language")
            }

            val botJson = JsonObject()
            botJson.addProperty("power", true)
            botJson.addProperty("name", projectName)
            botJson.addProperty("language", language.name)

            FileStream.write(
                "${dirPath.absolutePath}/bot.json",
                CoreHelper.gson.fromJson(botJson, String::class.java)
            )

            val projectListPath = "${appPath.absolutePath}/project.json"
            val projectListDB = File(projectListPath)
            if (!projectListDB.exists()) {
                val jsonArray = JsonArray()
                val jsonObject = JsonObject()
                jsonObject.addProperty("name", projectName)
                jsonObject.addProperty("path", dirPath.absolutePath)
                jsonArray.add(jsonObject)
                FileStream.write(projectListPath, CoreHelper.gson.fromJson(jsonArray, String::class.java))
            } else {
                val jsonArray = CoreHelper.gson.fromJson(FileStream.read(projectListPath), JsonArray::class.java)
                val jsonObject = JsonObject()
                jsonObject.addProperty("name", projectName)
                jsonObject.addProperty("path", dirPath.absolutePath)
                jsonArray.add(jsonObject)
                FileStream.write(projectListPath, CoreHelper.gson.fromJson(jsonArray, String::class.java))
            }
            true
        } catch (e: Exception) {
            Logger.e("Project Init Error: ${e.message} stackTrace = ${e.stackTraceToString()}")
            false
        }
    }

    fun makeModuleDir(projectName: String): Boolean {
        return try {
            if (!isExistsModuleDir(projectName)) {
                File(CoreHelper.sdcardPath).resolve(CoreHelper.directoryName).resolve("Projects").resolve(projectName).resolve("node_modules").mkdirs()
                return true
            } else true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isExistsProject(projectName: String): Boolean {
        return try {
            val path = File("${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/Projects/$projectName/script.js")
            path.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isExistsModuleDir(projectName: String): Boolean {
        return try {
            val path = File("${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/Projects/$projectName/node_modules")
            path.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun installNodePackage() {
        val gnm = File("${CoreHelper.filesDir}/global.js")
        if (!gnm.exists()) {
            FileStream.write("${CoreHelper.filesDir}/global.js", """
                    globalThis.process = require('/data/user/0/com.skyline.msgbot/files/languages/nodejs/process');
                    globalThis.Buffer = require('/data/user/0/com.skyline.msgbot/files/languages/nodejs/buffer').Buffer;
                """.trimIndent())
        }

        val nmPath = File(CoreHelper.baseNodePath)
        Logger.d(nmPath.exists())
        if (!nmPath.exists()) {
            Logger.d("Noting :(")
            try {
                NodeModuleUtil.installNodeModule()
            } catch (e: Exception) {
                Logger.e("Already?")
            }
        }
    }

}
