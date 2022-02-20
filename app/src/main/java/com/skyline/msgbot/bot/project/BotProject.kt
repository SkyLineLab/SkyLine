/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.project

import com.skyline.msgbot.bot.client.BotClient
import com.skyline.msgbot.bot.runtime.RuntimeManager

class BotProject(val runtimeID: Number) {
    fun getClient(): BotClient? {
        return RuntimeManager.clients[runtimeID];
    }

    fun on(): Boolean {
        RuntimeManager.runtimes.remove(runtimeID)
        return true
    }
}