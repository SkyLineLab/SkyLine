/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.runtime

import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.bot.client.BotClient
import com.skyline.msgbot.bot.project.ProjectInitUtil
import com.skyline.msgbot.bot.util.ApiApplyUtil
import com.skyline.msgbot.bot.util.ContextUtils
import com.skyline.msgbot.bot.util.SDCardUtils
import com.skyline.msgbot.setting.Constants
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.Source

internal object RuntimeManager {
    val runtimes: HashMap<Number, Context> = hashMapOf()
    val clients: HashMap<Number, BotClient> = hashMapOf()
    val powerMap: HashMap<Number, Boolean> = hashMapOf()
    val projectIds: HashMap<Number, String> = hashMapOf()
    val projectNames: HashMap<String, Number> = hashMapOf()

    fun addRuntime(projectName: String): Boolean {
        val res = ProjectInitUtil.createProject(projectName)
        ProjectInitUtil.makeModuleDir()
        if (!res && !ProjectInitUtil.isExistsProject(projectName)) {
            println("Create Project Error")
            return false
        } else {
            val size: Int = runtimes.count() + 1
            val context: Context = ContextUtils.getJSContext()
            ApiApplyUtil.applyBotApi(context.getBindings("js"), true, size)
            runtimes[size] = context
            powerMap[size] = true
            clients[size] = BotClient()
            projectIds[size] = projectName
            projectNames[projectName] = size
            runtimes[size]?.eval(
                Source.create("js", FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/$projectName/script.js"))
            )
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
                Source.create("js", FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/$project/script.js"))
            )
            true
        }
    }
}