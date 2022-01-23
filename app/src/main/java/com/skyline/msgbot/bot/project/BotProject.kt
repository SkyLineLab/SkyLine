/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.project

import com.skyline.msgbot.bot.client.BotClient
import com.skyline.msgbot.bot.runtime.RuntimeManager

class BotProject(val runtimeID: Number) {
    fun getClient(): BotClient? {
        return RuntimeManager.clients[runtimeID];
    }
}