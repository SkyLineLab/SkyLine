/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.event

/**
 * Event String 모음집
 */
class Events {
    companion object {
        @JvmStatic
        val ON_MESSAGE = "message"
        @JvmStatic
        val BOT_ON = "boton"
        @JvmStatic
        val BOT_OFF = "botoff"
        @JvmStatic
        val ON_NOTIFICATION_POSTED = "onNotificationPosted"
    }
}