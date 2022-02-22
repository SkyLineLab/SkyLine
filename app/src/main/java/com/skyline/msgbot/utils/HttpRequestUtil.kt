/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist

object HttpRequestUtil {
    /**
     * 반드시 새 쓰레드에서 실행하세요!
     */
    fun request(url: String): String {
       val html = Jsoup.connect(url).get()
        html.outputSettings(Document.OutputSettings().prettyPrint(false))
        html.select("br").append("\\n")
        html.select("p").append("\\n\\n")
        val str = html.html().replace("\\\\n", "\n")
        return Jsoup.clean(str, "", Whitelist.none(), Document.OutputSettings().prettyPrint(false)).replace("&amp;", "&")
            .replace("&gt;", ">")
            .replace("&lt;", "<")
    }
}