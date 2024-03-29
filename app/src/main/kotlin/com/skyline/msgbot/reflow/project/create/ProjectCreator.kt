package com.skyline.msgbot.reflow.project.create

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.reflow.project.exists.ProjectExistsChecker
import com.skyline.msgbot.reflow.script.JSEngineType
import com.skyline.msgbot.reflow.script.api.FileStream
import java.io.File

object ProjectCreator {

    fun createProject(projectName: String, type: JSEngineType) {
        if (ProjectExistsChecker.existsProject(projectName)) {
            throw IllegalArgumentException("Project $projectName already exists")
        } else {
            createSkylineDir()
            appendProjectsJson(projectName, type)
            createDir(projectName)
            createJsonInProject(projectName, type)
            createScript(projectName)
            initNodeEnvironment(projectName)
        }
    }

    private fun appendProjectsJson(projectName: String, type: JSEngineType) {
        val projectJson = JsonObject()
        val json = if (!FileStream.exists("projects.json")) {
            JsonObject().apply {
                add("projects", JsonArray())
            }
        } else {
            CoreHelper.GSON.fromJson(
                FileStream.read("projects.json"),
                JsonObject::class.java
            )
        }
        val projects = json.getAsJsonArray("projects")

        when (type) {
            JSEngineType.GRAALVM_JS -> {
                projectJson.addProperty("name", projectName)
                projectJson.addProperty("type", JSEngineType.GRAALVM_JS.name)
                projects.add(projectJson)
            }

            JSEngineType.V8 -> {
                projectJson.addProperty("name", projectName)
                projectJson.addProperty("type", JSEngineType.V8.name)
                projects.add(projectJson)
            }
        }

        FileStream.write("projects.json", CoreHelper.GSON.toJson(json))
    }

    private fun createSkylineDir() {
        val skylineDir = File("${CoreHelper.sdcardPath}/skyline")
        if (!skylineDir.exists()) {
            skylineDir.mkdirs()
        }
    }

    private fun createDir(projectName: String) {
        File("${CoreHelper.sdcardPath}/skyline/Projects/$projectName").mkdirs()
    }

    private fun createJsonInProject(projectName: String, type: JSEngineType) {
        val json = if (!FileStream.exists("Projects/${projectName}/project.json")) {
            JsonObject()
        } else {
            CoreHelper.GSON.fromJson(FileStream.read("Projects/${projectName}/project.json"), JsonObject::class.java)
        }

        when (type) {
            JSEngineType.GRAALVM_JS -> {
                json.addProperty("name", projectName)
                json.addProperty("type", JSEngineType.GRAALVM_JS.name)
            }

            JSEngineType.V8 -> {
                json.addProperty("name", projectName)
                json.addProperty("type", JSEngineType.V8.name)
            }
        }

        FileStream.write("Projects/$projectName/project.json", CoreHelper.GSON.toJson(json))
    }

    private fun createScript(projectName: String) {
        val context = CoreHelper.contextGetter!!.invoke()
        val script = context.assets.open("jsInitScript.js").bufferedReader().use { it.readText() }
        FileStream.write("Projects/$projectName/script.js", script)
    }

    private fun initNodeEnvironment(projectName: String) {
        File("Projects/${projectName}/node_modules").mkdir()
    }

}