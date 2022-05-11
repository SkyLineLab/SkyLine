/*
 * MIT License
 *
 * Copyright (c) 2022 SkyLineLab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.skyline.msgbot.script.client

import com.skyline.msgbot.script.api.Event
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
        synchronized(BotClient::class.java) {
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
        synchronized(BotClient::class.java) {
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