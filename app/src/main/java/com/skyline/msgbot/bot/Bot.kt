/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot

import com.skyline.msgbot.bot.event.MessageEvent
import com.skyline.msgbot.bot.runtime.RuntimeManager
import org.graalvm.polyglot.Source
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Bot {
    private var pool: ExecutorService = Executors.newCachedThreadPool()

    fun callOnMessage(data: MessageEvent) {
        val messageEvent = arrayOf(data)
        RuntimeManager.clients.forEach { (key, value) ->
            println("key = $key value = $value power = ${RuntimeManager.powerMap[key]}")
            if (RuntimeManager.powerMap[key] == true) {
                println("call message!")
                if (pool.isShutdown) {
                    pool = Executors.newCachedThreadPool()
                }
                pool.execute {
                    value.emit("message", *messageEvent)
                }
            }
        }
    }

    fun destroyThread() {
        if (!pool.isShutdown) {
            pool.shutdown()
        }
    }

    @Deprecated(message = "컴파일 함수는 RuntimeManager로 이동")
    fun compile(runtimeId: Number): Boolean {
        if (RuntimeManager.runtimes[runtimeId] == null) return false
        else {
            RuntimeManager.runtimes[runtimeId]?.eval(
                Source.create(
                    "js",
                    "TODO" //TODO(input Source Code)
                )
            )
        }
        return true
    }
}