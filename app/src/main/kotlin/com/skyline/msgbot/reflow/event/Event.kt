package com.skyline.msgbot.reflow.event

interface Event {
    val isTrusted: Boolean
    val type: String
    val timeStamp: Long
    val defaultPrevented: Boolean
    val scope: String
}