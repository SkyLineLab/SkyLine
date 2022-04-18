/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.util

import org.graalvm.polyglot.Value
import org.graalvm.polyglot.proxy.ProxyExecutable

/**
 * GraalJS Promise Util
 */
object JSPromise {
    fun wrapPromise(result: Any, isSuccess: Boolean): Value {
        val promiseConstructor = JSGlobal.global.getMember("Promise")
        return promiseConstructor.newInstance(ProxyExecutable { arguments: Array<Value> ->
            val resolve = arguments[0]
            val reject = arguments[1]
            if (isSuccess) {
                resolve.execute(result)
            } else reject.execute(result)
            null
        })
    }
}