/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.project

import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.bot.script.ScriptLanguage
import com.skyline.msgbot.nodejs.utils.NodeJSModuleInitUtils
import com.skyline.msgbot.utils.SDCardUtils
import com.skyline.msgbot.setting.Constants
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object ProjectInitUtil {
    fun createProject(projectName: String, language: ScriptLanguage): Boolean {
        if (isExistsProject(projectName)) return false
        return try {
            val appPath = File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects")
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
                val jsonObject = JSONObject()
                    .put("name", projectName)
                    .put("version", "1.0.0")
                    .put("dependencies", JSONObject())
                FileStream.write(packageJsonPath, jsonObject.toString(4))
            }
            when (language) {
                ScriptLanguage.JAVASCRIPT -> {
                    FileStream.write("${dirPath.absolutePath}/script.js", Constants.jsInitScript)
                }

                ScriptLanguage.TYPESCRIPT -> {
                    FileStream.write("${dirPath.absolutePath}/script.ts", Constants.tsInitScript)
                }

                else -> throw UnsupportedOperationException("Not Support Language: $language")
            }
            NodeJSModuleInitUtils.initAllModule(projectName)
            FileStream.write(
                "${dirPath.absolutePath}/bot.json",
                JSONObject().put("power", true)
                    .put("name", projectName)
                    .put("language", language)
                    .toString(4)
            )
            val projectListPath = "${appPath.absolutePath}/project.json"
            val projectListDB = File(projectListPath)
            return if (!projectListDB.exists()) {
                val jsonArray = JSONArray().put(
                    JSONObject().put("name", projectName)
                        .put("path", dirPath.path)
                )
                FileStream.write(projectListPath, jsonArray.toString(4))
            } else {
                val jsonArray = JSONArray(FileStream.read(projectListPath)).put(
                    JSONObject().put("name", projectName)
                        .put("path", dirPath)
                )
                FileStream.write("${appPath.absolutePath}/project.json", jsonArray.toString(4))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun makeModuleDir(projectName: String): Boolean {
        return try {
            if (!isExistsModuleDir(projectName)) {
                File(SDCardUtils.sdcardPath).resolve(Constants.directoryName).resolve("Projects").resolve(projectName).resolve("node_modules").mkdirs()
                return true
            } else true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isExistsProject(projectName: String): Boolean {
        return try {
            val path: File = File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$projectName/script.js")
            path.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun isExistsModuleDir(projectName: String): Boolean {
        return try {
            val path = File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$projectName/node_modules")
            path.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}