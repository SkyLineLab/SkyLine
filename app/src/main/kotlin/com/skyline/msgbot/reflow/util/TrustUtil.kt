package com.skyline.msgbot.reflow.util

object TrustUtil {

    fun getIsTrust(): Boolean {
        try {
            throw RuntimeException()
        } catch (e: Exception) {
            val str = e.stackTraceToString()
            return !str.contains("com.oracle.truffle.api")
        }
    }

}