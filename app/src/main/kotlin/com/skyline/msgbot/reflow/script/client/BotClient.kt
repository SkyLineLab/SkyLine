package com.skyline.msgbot.reflow.script.client

import com.caoccao.javet.values.V8Value
import com.caoccao.javet.values.reference.V8ValueFunction
import com.orhanobut.logger.Logger
import com.skyline.msgbot.reflow.project.Project
import com.skyline.msgbot.reflow.script.JSEngineType
import com.skyline.msgbot.reflow.util.ToStringUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.graalvm.polyglot.Value

class BotClient(
    val type: JSEngineType,
) {

    private val eventMap: HashMap<String, Any> = hashMapOf()

    fun on(eventName: String, callback: Any): BotClient {
        Logger.d("is Value: ${ callback is Value }")
        eventMap[eventName] = callback
        Logger.d("on!")
        return this
    }

    fun addListener(eventName: String, callback: Any): BotClient {
        return on(eventName, callback)
    }

    @Synchronized
    fun emit(event: String, vararg arguments: Any): Boolean {
        synchronized(BotClient::class.java) {
            if (!eventMap.containsKey(event)) return false

            CoroutineScope(Dispatchers.IO).launch {
                val callback = eventMap[event]

                when (type) {
                    JSEngineType.GRAALVM_JS -> {
                        Logger.d("is Value!")

                        Value.asValue(callback).executeVoid(*arguments) // TODO
                    }

                    JSEngineType.V8 -> {
                        (callback as V8ValueFunction).callExtended<V8Value>(null, false, *arguments) // TODO
                    }
                }
            }

            return true
        }
    }

    override fun toString(): String {
        return ToStringUtil.toStringFormat(this)
    }

}