package com.skyline.msgbot.reflow.script.javascript

import com.caoccao.javet.values.reference.V8ValuePromise
import com.skyline.msgbot.reflow.script.JSEngineType

object JSPromise {

    fun createPromiseObject(type: JSEngineType) {
        when (type) {
            JSEngineType.GRAALVM_JS -> {
                TODO()
            }

            JSEngineType.V8 -> {
                
            }

            JSEngineType.RHINO -> {
                TODO()
            }
        }
    }

}