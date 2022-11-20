package com.skyline.msgbot.reflow.event.message

interface MessageChannel {

    val name: String

    val isGroupChat: Boolean

    fun send(message: Any?): Any

    fun sendAllRoom(message: Any?): Any

    fun markAsRead(): Any


}