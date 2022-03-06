/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.runtime

import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.bot.client.BotClient
import com.skyline.msgbot.bot.project.ProjectInitUtil
import com.skyline.msgbot.bot.script.ScriptLanguage
import com.skyline.msgbot.bot.script.TypeScript
import com.skyline.msgbot.bot.util.ApiApplyUtil
import com.skyline.msgbot.bot.util.ContextUtils
import com.skyline.msgbot.utils.SDCardUtils
import com.skyline.msgbot.setting.Constants
import kotlinx.coroutines.runBlocking
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source

internal object RuntimeManager {
    val runtimes: HashMap<Number, Context> = hashMapOf()
    val clients: HashMap<Number, BotClient> = hashMapOf()
    val powerMap: HashMap<Number, Boolean> = hashMapOf()
    val projectIds: HashMap<Number, String> = hashMapOf()
    val projectNames: HashMap<String, Number> = hashMapOf()
    val projectLanguage: HashMap<Number, ScriptLanguage> = hashMapOf()

    fun addRuntime(projectName: String, language: ScriptLanguage): Boolean {
        val res = ProjectInitUtil.createProject(projectName, language)
        ProjectInitUtil.makeModuleDir(projectName)
        if (!res && !ProjectInitUtil.isExistsProject(projectName)) {
            println("Create Project Error")
            return false
        } else {
            val size: Int = runtimes.count() + 1
            val context: Context = ContextUtils.getJSContext(projectName)
            ApiApplyUtil.applyBotApi(context.getBindings("js"), true, size)
            runtimes[size] = context
            powerMap[size] = true
            clients[size] = BotClient()
            projectIds[size] = projectName
            projectNames[projectName] = size
            when (language) {
                ScriptLanguage.JAVASCRIPT -> {
                    projectLanguage[size] = language
                    runtimes[size]?.eval(
                        Source.create(
                            "js",
                            FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$projectName/script.js")
                        )
                    )
                }

                ScriptLanguage.TYPESCRIPT -> {
                    projectLanguage[size] = language
                    Thread {
                        val originalScript = FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$projectName/script.ts") ?: ""
                        val convertedScript = TypeScript.convertScript(originalScript)
                        runtimes[size]?.eval(
                            Source.create(
                                "js",
                                convertedScript
                            )
                        )
                    }.start()
                }

                else -> throw UnsupportedOperationException("Not Support Language: $language")
            }
            return true
        }
    }

    fun hasRuntime(projectName: String): Boolean {
        return runtimes[projectNames[projectName]] != null
    }

    fun getIsPowerOn(number: Number): Boolean {
        val boolean: Boolean = if (powerMap[number] == null) {
            false
        } else {
            powerMap[number] ?: false
        }

        return boolean
    }

    fun compile(project: String): Boolean {
        return if (projectNames[project] == null) false
        else {
            runtimes[projectNames[project]]?.eval(
                Source.create("js", FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$project/script.js"))
            )
            true
        }
    }
}