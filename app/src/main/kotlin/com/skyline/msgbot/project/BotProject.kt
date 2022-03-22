/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.project

import com.skyline.msgbot.runtime.RuntimeManager
import com.skyline.msgbot.script.client.BotClient
import org.graalvm.polyglot.Context

class BotProject(val runtimeID: Int) {
    fun getClient(): BotClient? {
        return RuntimeManager.clients[runtimeID]
    }

    fun getAllProjectNames(): List<String> {
        return RuntimeManager.projectNames.keys.toList()
    }

    fun getCurrentProjectId(): Number {
        return runtimeID
    }

    fun getScriptContext(): Context? {
        return RuntimeManager.runtimes[runtimeID]
    }
}