/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.script

import org.json.JSONObject
import org.jsoup.Jsoup

object TypeScript {
    fun convertScript(script: String): String {
        val req = Jsoup.connect("https://api.extendsclass.com/convert/typescript/javascript").requestBody(script).post().text()
        val json = JSONObject(req)
        return json.getString("outputText")
    }
}