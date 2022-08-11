package com.skyline.msgbot.reflow.script.api

import com.skyline.msgbot.core.CoreHelper
import java.io.File

object FileStream {

    fun write(path: String, content: String): Boolean {
        val file = File(path)
        file.printWriter().use { it.print(content) }
        return true
    }

    fun read(path: String): String {
        try {
            val realPath = if (!path.startsWith("/")) {
                "${CoreHelper.sdcardPath}/skyline/${path}"
            } else path
            val bufferedReader = File(realPath).bufferedReader()
            return bufferedReader.use { it.readText() }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
    
    fun exists(path: String): Boolean {
        val realPath = if (!path.startsWith("/")) {
            "${CoreHelper.sdcardPath}/skyline/${path}"
        } else path
        return File(realPath).exists()
    }

}