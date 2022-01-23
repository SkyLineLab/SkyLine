/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.setting

object Constants {
    val allowPackageNames: List<String> = listOf("com.kakao")
    const val directoryName: String = "horizon"
    const val initScript: String = "const client = BotProject.getClient();\n" +
            "\n" +
            "client.on('message', (data) => {\n" +
            "    if (data.message === '!ping') {\n" +
            "        data.room.send('pong!');\n" +
            "    }\n" +
            "});"
}