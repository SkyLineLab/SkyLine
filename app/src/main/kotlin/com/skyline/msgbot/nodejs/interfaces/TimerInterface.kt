/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.nodejs.interfaces

import org.graalvm.polyglot.Value

class TimerInterface {
    companion object {
        @FunctionalInterface
        interface SetTimeoutFunction<callback: Value, delay: Number, args: List<Value?>> {
            fun apply(callback: callback, delay: delay, args: args): Number
            fun apply(callback: callback, delay: delay): Number
        }

        @FunctionalInterface
        interface SetImmediateFunction<callback: Value, args: List<Value?>> {
            fun apply(callback: callback, args: args): Number
            fun apply(callback: callback): Number
        }
    }
}