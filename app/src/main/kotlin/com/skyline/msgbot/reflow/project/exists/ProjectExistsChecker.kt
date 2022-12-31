package com.skyline.msgbot.reflow.project.exists

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.reflow.script.api.FileStream
import java.io.File

object ProjectExistsChecker {

    fun existsProject(projectName: String): Boolean {
        return checkInAllProjectJson(projectName) && checkExistsDir(projectName) && checkProjectJson(projectName)
    }

    private fun checkInAllProjectJson(projectName: String): Boolean {
        try {
            if (!FileStream.exists("projects.json")) return false

            val json = CoreHelper.GSON.fromJson(
                FileStream.read("projects.json"),
                JsonObject::class.java
            )

            if (!json.has("projects")) return false

            json.getAsJsonArray("projects").forEach {
                val obj = it.asJsonObject
                val name = obj["name"].asString

                if (name == projectName) {
                    return true
                }
            }

            return false
        } catch (e: JsonSyntaxException) {
            return false
        }
    }

    private fun checkExistsDir(projectName: String): Boolean {
        val file = File("${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/Projects/$projectName")

        return file.isDirectory
    }

    private fun checkProjectJson(projectName: String): Boolean {
        if (!FileStream.exists("Projects/$projectName/project.json")) return false

        val jsonTxt = FileStream.read("Projects/$projectName/project.json")

        val json = CoreHelper.GSON.fromJson(
            jsonTxt,
            JsonObject::class.java
        )

        return json.has("name") && json.has("type")
    }

}