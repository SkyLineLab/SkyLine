/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.client

import org.graalvm.polyglot.Value

class BotClient() {
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
                println("emit = $event")
                if (!eventMap.containsKey(event)) return false
                val func: Value? = eventMap[event]
                println("debug = ${arguments.toString()}")
                func?.executeVoid(*arguments)
                println("call!")
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }
    }
}