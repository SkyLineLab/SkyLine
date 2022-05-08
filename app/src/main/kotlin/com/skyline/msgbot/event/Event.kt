/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.event

interface Event {
    val isTrusted: Boolean
    val type: String
    val timeStamp: Long
    val defaultPrevented: Boolean
    val scope: String
}