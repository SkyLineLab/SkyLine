package com.skyline.msgbot.reflow.project

import com.skyline.msgbot.reflow.script.JSEngineType
import com.skyline.msgbot.reflow.script.client.BotClient

class Project(
    val type: JSEngineType,
    val name: String,
    private val context: Any,
    val client: BotClient
) {

    @Suppress("UNCHECKED_CAST")
    fun <T> getEngineContext(): T {
        return context as T
    }

}