/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.project

import android.os.Environment
import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.setting.Constants
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object ProjectInitUtil {
    private val sdcardPath = Environment.getExternalStorageDirectory().absolutePath

    fun createProject(projectName: String): Boolean {
        if (isExistsProject(projectName)) return false
        return try {
            val appPath: String = "$sdcardPath/${Constants.directoryName}"
            val dirPath: File = File("$appPath/$projectName")
            if (!dirPath.exists()) {
                dirPath.mkdir()
            }
            FileStream.write("${dirPath.absolutePath}/script.js", Constants.initScript)
            FileStream.write(
                "${dirPath.absolutePath}/bot.json",
                JSONObject().put("power", true)
                    .put("name", projectName).toString(4)
            )
            val projectListPath = "$sdcardPath/${Constants.directoryName}/project.json"
            val projectListDB: File = File(projectListPath)
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
                FileStream.write("${sdcardPath}/${Constants.directoryName}/project.json", jsonArray.toString(4))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isExistsProject(projectName: String): Boolean {
        return try {
            val path: File = File("$sdcardPath/${Constants.directoryName}/$projectName/script.js")
            path.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}