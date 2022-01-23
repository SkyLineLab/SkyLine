/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.runtime

import android.os.Environment
import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.bot.client.BotClient
import com.skyline.msgbot.bot.project.ProjectInitUtil
import com.skyline.msgbot.bot.util.ApiApplyUtil
import com.skyline.msgbot.setting.Constants
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.Source

internal object RuntimeManager {
    val runtimes: HashMap<Number, Context> = hashMapOf()
    val clients: HashMap<Number, BotClient> = hashMapOf()
    val powerMap: HashMap<Number, Boolean> = hashMapOf()
    val projectNames: HashMap<String, Number> = hashMapOf()
    private val sdcardPath = Environment.getExternalStorageDirectory().absolutePath

    fun addRuntime(projectName: String): Boolean {
        val res = ProjectInitUtil.createProject(projectName)
        if (!res && !ProjectInitUtil.isExistsProject(projectName)) {
            println("Create Project Error")
            return false
        } else {
            val size: Int = runtimes.count() + 1
            val context: Context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.ALL)
                .allowExperimentalOptions(true)
                .option("js.nashorn-compat", "true")
                .option("js.ecmascript-version", "2022")
                .allowHostClassLookup { true }.build()
            ApiApplyUtil.applyBotApi(context.getBindings("js"), true, size)
            runtimes[size] = context
            powerMap[size] = true
            clients[size] = BotClient()
            projectNames[projectName] = size
            runtimes[size]?.eval(
                Source.create("js", FileStream.read("$sdcardPath/${Constants.directoryName}/$projectName/script.js"))
            )
            return true
        }
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
                Source.create("js", FileStream.read("$sdcardPath/${Constants.directoryName}/$project/script.js"))
            )
            true
        }
    }
}