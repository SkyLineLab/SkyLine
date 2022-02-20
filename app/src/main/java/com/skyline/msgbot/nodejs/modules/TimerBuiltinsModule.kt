/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.nodejs.modules

import com.skyline.msgbot.nodejs.NodeModule
import com.skyline.msgbot.nodejs.interfaces.TimerInterface
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.Value
import java.util.*
import java.util.concurrent.Phaser
import java.util.function.Function
import kotlin.collections.HashMap

/**
 * Node Builtin Module "timer"
 * @author naijun
 */
@NodeModule(name = "timers")
class TimerBuiltinsModule {
    companion object {
        class SetTimeOut : TimerInterface.Companion.SetTimeoutFunction<Value, Number, List<Value?>> {
            override fun apply(callback: Value, delay: Number, args: List<Value?>): Number {
                return setTimeout(callback, delay, *args.toTypedArray())
            }

            override fun apply(callback: Value, delay: Number): Number {
                return setTimeout(callback, delay)
            }
        }

        private val phaser = Phaser();

        private var timeoutStack = 0
        private val timerMap: HashMap<Number, Timer> = hashMapOf()

        private fun pushTimeout() {
            timeoutStack++
        }

        private fun popTimeout() {
            timeoutStack--
            if (timeoutStack > 0) return

            timerMap[timeoutStack]?.cancel()
            phaser.forceTermination()
        }

        @HostAccess.Export
        fun setTimeout(callback: Value, delay: Number): Number {
            return setTimeout(callback, delay, null as Value?)
        }

        @HostAccess.Export
        fun setTimeout(callback: Value, delay: Number, vararg args: Value?): Number {
            val phase = phaser.register()
            val timer = Timer()
            val canceled = false

            timer.schedule(object : TimerTask() {
                override fun run() {
                    if (canceled) {
                        timer.cancel()
                        return
                    }

                    try {
                        callback.executeVoid(*args)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        phaser.arriveAndDeregister()
                        popTimeout()
                    }
                }
            }, delay.toLong())

            pushTimeout()
            timerMap[timeoutStack] = timer
            return timeoutStack;
        }

        @HostAccess.Export
        fun clearTimeout(stackId: Number) {
            timerMap[stackId]?.cancel()
            popTimeout()
        }

        @HostAccess.Export
        fun setInterval(callback: Value, delay: Number, vararg args: Value?) {
            fun loop() {
                setTimeout(callback, delay)
            }
        }
    }
}