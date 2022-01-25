/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.client

import com.skyline.msgbot.bot.event.MessageEvent
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

    fun emit(event: String, arguments: MessageEvent): Boolean { //vararg arguments: Any
        println("emit = $event")
        if (!eventMap.containsKey(event)) return false
        object : Thread() {
            override fun run() {
                val func: Value? = eventMap[event]
                func?.executeVoid(arguments)
            }
        }.start()
        println("call!")
        return true
    }
}