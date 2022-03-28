/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.project

import com.skyline.msgbot.runtime.RuntimeManager
import com.skyline.msgbot.script.client.BotClient
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.proxy.ProxyArray

class BotProject(private val runtimeID: Int) {
    fun getClient(): BotClient? {
        return RuntimeManager.clients[runtimeID]
    }

    fun getAllProjectNames(): ProxyArray {
        return ProxyArray.fromList(RuntimeManager.projectNames.keys.toList())
    }

    fun getCurrentProjectId(): Number {
        return runtimeID
    }

    fun getScriptContext(): Context? {
        return RuntimeManager.runtimes[runtimeID]
    }

    fun on(): Boolean {
        RuntimeManager.powerMap[runtimeID] = true
        return true
    }

    fun off(): Boolean {
        // TODO
        return false
    }

    fun compile(): Boolean {
        RuntimeManager.compile(RuntimeManager.projectIds[runtimeID]!!)
        return true
    }

    override fun toString(): String {
        return "[object BotProject]"
    }
}