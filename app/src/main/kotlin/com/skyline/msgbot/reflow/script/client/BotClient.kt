package com.skyline.msgbot.reflow.script.client

import com.caoccao.javet.values.V8Value
import com.caoccao.javet.values.reference.V8ValueFunction
import org.graalvm.polyglot.Value

class BotClient {

    private val eventMap: HashMap<String, Any> = hashMapOf()

    fun on(eventName: String, callback: Any): BotClient {
        eventMap[eventName] = callback
        return this
    }

    fun addListener(eventName: String, callback: Any): BotClient {
        return on(eventName, callback)
    }

    fun emit(event: String, vararg arguments: Any): Boolean {
        val callback = eventMap[event]

        if (callback is org.mozilla.javascript.Function) {
            TODO("Rhino is not support yet")
        }

        if (callback is V8ValueFunction) {
            callback.callExtended<V8Value>(null, false, arguments) // TODO
        }

        if (callback is Value) {
            callback.executeVoid(arguments) // TODO
        }

        return true
    }

}