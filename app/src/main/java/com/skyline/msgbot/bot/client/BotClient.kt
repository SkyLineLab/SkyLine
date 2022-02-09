/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
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

    fun emit(event: String, vararg arguments: Any): Boolean { //vararg arguments: Any
        try {
            println("emit = $event")
            if (!eventMap.containsKey(event)) return false
            val func: Value? = eventMap[event]
            println("debug = ${arguments.toString()}")
            func?.executeVoid(*arguments)
            println("call!")
            return true
        } catch (e: IllegalStateException) {
            return false
        }
    }
}