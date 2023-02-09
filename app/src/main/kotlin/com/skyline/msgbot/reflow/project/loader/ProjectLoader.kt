package com.skyline.msgbot.reflow.project.loader

import com.google.gson.JsonObject
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.reflow.project.Project
import com.skyline.msgbot.reflow.project.ProjectNotFoundException
import com.skyline.msgbot.reflow.script.JSEngineType
import com.skyline.msgbot.reflow.script.api.BotProject
import com.skyline.msgbot.reflow.script.api.FileStream
import com.skyline.msgbot.reflow.script.client.BotClient
import com.skyline.msgbot.reflow.script.util.ApiApplyUtil
import com.skyline.msgbot.repository.JSEngineRepository
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source
import java.io.File

object ProjectLoader {

    fun load(projectName: String): Project {
        val json = CoreHelper.GSON.fromJson(
            FileStream.read("projects.json"),
            JsonObject::class.java
        )

        val array = json.getAsJsonArray("projects")

        var instance: Project? = null

        array.forEach {
            val obj = it.asJsonObject
            val name = obj.get("name").asString

            if (projectName == name) {
                val typeStr = obj.get("type").asString
                val type = JSEngineType.valueOf(typeStr)
                val client = BotClient(type)

                val context: Any = when (type) {

                    JSEngineType.GRAALVM_JS -> {
                        JSEngineRepository.getGraalJSEngine(projectName)
                    }

                    JSEngineType.V8 -> {
                        JSEngineRepository.getV8JSEngine()
                    }

                }

                val project = Project(type, projectName, context, client)
                instance = project
                val botProject = BotProject(
                    project,
                    client
                )

                ApiApplyUtil.applyApi(project, botProject)

                when (type) {
                    JSEngineType.GRAALVM_JS -> {
                        val scriptContext = project.getEngineContext<Context>()
                        scriptContext.eval(
                            Source.newBuilder(
                                "js",
                                File(
                                    "${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/Projects/${projectName}/script.js"
                                )
                            ).mimeType("application/javascript+module").build()
                        )

//                        scriptContext.eval("js", "console.log(this)")
                    }

                    JSEngineType.V8 -> {
                        TODO("Not Implement Yet")
                    }
                }
            }
        }

        if (instance == null) throw ProjectNotFoundException(projectName)

        return instance as Project
    }

}