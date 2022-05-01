/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.script.client

import com.skyline.msgbot.script.api.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.graalvm.polyglot.Value
import org.graalvm.polyglot.HostAccess

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

    @HostAccess.DisableMethodScoping
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

    @Synchronized
    fun dispatchEvent(event: Event): Boolean {
        kotlin.synchronized(BotClient::class.java) {
            try {
                if (!eventMap.containsKey(event.eventName)) return false
                CoroutineScope(Dispatchers.IO).launch {
                    val func: Value? = eventMap[event.eventName]
                    func?.executeVoid(event.data)
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