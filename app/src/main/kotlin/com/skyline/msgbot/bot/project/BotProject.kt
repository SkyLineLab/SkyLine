/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.project

import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.bot.client.BotClient
import com.skyline.msgbot.bot.runtime.RuntimeManager
import com.skyline.msgbot.bot.script.ScriptLanguage
import com.skyline.msgbot.bot.script.TypeScript
import com.skyline.msgbot.bot.util.ApiApplyUtil
import com.skyline.msgbot.bot.util.ContextUtils
import com.skyline.msgbot.setting.Constants
import com.skyline.msgbot.utils.SDCardUtils
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source

class BotProject(val runtimeID: Number) {
    fun getClient(): BotClient? {
        return RuntimeManager.clients[runtimeID];
    }

    fun getAllProjectNames(): List<String> {
        return RuntimeManager.projectNames.keys.toList()
    }

    fun getCurrentProjectId(): Number {
        return runtimeID
    }

    fun getCurrentProjectContext(): Context? {
        return RuntimeManager.runtimes[runtimeID]
    }

    fun compileAll(): Boolean {
        try {
            Thread {
                for (runtime in RuntimeManager.runtimes) {
                    println(runtime)
                    val cx = ContextUtils.getJSContext(RuntimeManager.projectIds[runtime.key]!!)
                    ApiApplyUtil.applyBotApi(cx.getBindings("js"), true, runtime.key)
                    RuntimeManager.runtimes[runtime.key] = cx
                    when (RuntimeManager.projectLanguage[runtime.key]) {
                        ScriptLanguage.JAVASCRIPT -> {
                            RuntimeManager.runtimes[runtime.key]?.eval(
                                Source.create(
                                    "js",
                                    FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/${RuntimeManager.projectIds[runtime.key]}/script.js")
                                )
                            )
                        }

                        ScriptLanguage.TYPESCRIPT -> {
                            val originalScript = FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/${RuntimeManager.projectIds[runtime.key]}/script.ts") ?: ""
                            val convertedScript = TypeScript.convertScript(originalScript)
                            RuntimeManager.runtimes[runtime.key]?.eval(
                                Source.create(
                                    "js",
                                    convertedScript
                                )
                            )
                        }

                        else -> {
                            // DO NOT ANYTHING
                        }
                    }
                }
            }.start()

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}