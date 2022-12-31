package com.skyline.msgbot.reflow.project

import com.google.gson.JsonObject
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.reflow.project.loader.ProjectLoader
import com.skyline.msgbot.reflow.script.api.FileStream

object ProjectManager {

    val list = hashMapOf<String, Project>()

    /**
     * 존재하는 프로젝트 로드
     */
    fun initialize(): HashMap<String, Project> {
        if (!FileStream.exists("projects.json")) throw RuntimeException("projects.json is not exists")

        val json = CoreHelper.GSON.fromJson(
            FileStream.read("projects.json"),
            JsonObject::class.java
        )

        if (!json.has("projects")) throw RuntimeException("projects is not exists")

        json.getAsJsonArray("projects").forEach {
            val obj = it.asJsonObject
            val name = obj["name"].asString

            list[name] = ProjectLoader.load(name)
        }

        return list
    }

    fun get(name: String): Project {
        return list[name] ?: throw ProjectNotFoundException("project $name is not found");
    }

}