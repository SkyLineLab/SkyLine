/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.setting

object Constants {
    val allowPackageNames: List<String> = listOf("com.kakao")
    const val directoryName: String = "skyline"
    const val jsInitScript: String = "const client = BotProject.getClient();\n" +
            "\n" +
            "client.on('message', (data) => {\n" +
            "    if (data.message === '!ping') {\n" +
            "        data.room.send('pong!');\n" +
            "    }\n" +
            "});"
    const val tsInitScript: String = "const client = BotProject.getClient()\n" +
            "\n" +
            "client.on('message', async (data: MessageEvent) => {\n" +
            "    if (data.message === '!ping') {\n" +
            "        await data.room.send('pong!')\n" +
            "    }\n" +
            "});"
}