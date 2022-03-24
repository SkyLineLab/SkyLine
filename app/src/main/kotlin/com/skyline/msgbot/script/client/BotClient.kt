/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.script.client

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.graalvm.polyglot.Value

class BotClient {
    private val eventMap: HashMap<String, Value> = hashMapOf()

    fun on(event: String, function: Value): BotClient {
        eventMap[event] = function
        return this
    }

    fun addListener(event: String, function: Value): BotClient {
        eventMap[event] = function
        return this
    }

    @Synchronized
    fun emit(event: String, vararg arguments: Any): Boolean { //vararg arguments: Any
        kotlin.synchronized(BotClient::class.java) {
            try {
                if (!eventMap.containsKey(event)) return false
                CoroutineScope(Dispatchers.IO).launch {
                    val func: Value? = eventMap[event]
                    func?.executeVoid(*arguments)
                }
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }
    }

    override fun toString(): String {
        return "[object BotClient]"
    }
}