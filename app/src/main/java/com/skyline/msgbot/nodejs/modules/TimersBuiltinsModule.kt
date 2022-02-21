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
class TimersBuiltinsModule {
    companion object {
        class SetTimeOut : TimerInterface.Companion.SetTimeoutFunction<Value, Number, List<Value?>> {
            override fun apply(callback: Value, delay: Number, args: List<Value?>): Number {
                return setTimeout(callback, delay, *args.toTypedArray())
            }

            override fun apply(callback: Value, delay: Number): Number {
                return setTimeout(callback, delay)
            }
        }

        class SetInterval : TimerInterface.Companion.SetTimeoutFunction<Value, Number, List<Value?>> {
            override fun apply(callback: Value, delay: Number, args: List<Value?>): Number {
                return setInterval(callback, delay, *args.toTypedArray())
            }

            override fun apply(callback: Value, delay: Number): Number {
                return setInterval(callback, delay)
            }
        }

        class SetImmediate : TimerInterface.Companion.SetImmediateFunction<Value, List<Value?>> {
            override fun apply(callback: Value, args: List<Value?>): Number {
                return setImmediate(callback, *args.toTypedArray())
            }

            override fun apply(callback: Value): Number {
                return setImmediate(callback)
            }
        }

        private val phaser = Phaser();

        private var timeoutStack = 0
        private val timerMap: HashMap<Number, Timer> = hashMapOf()
        private val timerPowerMap: HashMap<Number, Boolean> = hashMapOf()

        private var intervalStack = 0
        private val intervalMap: HashMap<Number, Timer> = hashMapOf()
        private val intervalPowerMap: HashMap<Number, Boolean> = hashMapOf()

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
                        if (!callback.canExecute()) {
                            return // not function
                        }

                        callback.executeVoid(*args)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        phaser.arriveAndDeregister()
                        popTimeout()
                        timerPowerMap[timeoutStack] = false
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
            if (timerPowerMap[timeoutStack] == true) {
                popTimeout()
                timerPowerMap[timeoutStack] = false
            }
        }

        private fun pushInterval() {
            intervalStack++
        }

        private fun popInterval() {
            intervalStack--
            if (intervalStack > 0) return

            intervalMap[intervalStack]?.cancel()
            phaser.forceTermination()
        }

        @HostAccess.Export
        fun setInterval(callback: Value, delay: Number, vararg args: Value?): Number {
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
                        if (!callback.canExecute()) {
                            return // not function
                        }

                        callback.executeVoid(*args)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }, delay.toLong(), delay.toLong())

            pushInterval()
            intervalPowerMap[intervalStack] = true
            intervalMap[intervalStack] = timer
            return intervalStack;
        }

        @HostAccess.Export
        fun clearInterval(stackId: Number) {
            intervalMap[stackId]?.cancel()
            phaser.arriveAndDeregister()
            if (intervalPowerMap[intervalStack] == true) {
                popInterval()
                intervalPowerMap[intervalStack] = false
            }
        }

        @HostAccess.Export
        fun setImmediate(callback: Value, vararg args: Value?): Number {
            return setTimeout(callback, 0, *args);
        }

        @HostAccess.Export
        fun clearImmediate(stackId: Number) {
            clearTimeout(stackId)
        }
    }
}