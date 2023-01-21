package com.skyline.msgbot.reflow.script.util

import com.skyline.msgbot.reflow.project.Project
import com.skyline.msgbot.reflow.script.JSEngineType
import com.skyline.msgbot.reflow.script.api.ScriptApp
import com.skyline.msgbot.reflow.script.api.BotProject
import com.skyline.msgbot.reflow.script.api.FileStream
import org.graalvm.polyglot.Context

object ApiApplyUtil {

    fun applyApi(project: Project, botProject: BotProject) {
        when (project.type) {
            JSEngineType.GRAALVM_JS -> {
                val context = project.getEngineContext<Context>()

                val global = context.getBindings("js")

                global.putMember("FileStream", FileStream)
                global.putMember("BotProject", botProject)
                global.putMember("App", ScriptApp)
            }

            JSEngineType.V8 -> {
                TODO("Not Implement")
            }
        }
    }

}