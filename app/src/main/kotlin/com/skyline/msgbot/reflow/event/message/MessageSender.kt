package com.skyline.msgbot.reflow.event.message

interface MessageSender {

    val name: String

    val profileBase64: String

    val profileHash: Int

    val userHash: String

}