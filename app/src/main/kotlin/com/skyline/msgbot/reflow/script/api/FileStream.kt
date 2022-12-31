package com.skyline.msgbot.reflow.script.api

import com.orhanobut.logger.Logger
import com.skyline.msgbot.core.CoreHelper
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

object FileStream {

    fun write(path: String, content: String): Boolean {
        val realPath = if (!path.startsWith("/")) {
            "${CoreHelper.sdcardPath}/skyline/${path}"
        } else path
        saveString(content, realPath, false)
        return true
    }

    fun read(path: String): String {
        val realPath = if (!path.startsWith("/")) {
            "${CoreHelper.sdcardPath}/skyline/${path}"
        } else path

        val file = File(realPath)
        if (!file.exists()) throw NoSuchFileException(file)

        val bufferedReader = file.bufferedReader()
        return bufferedReader.use { it.readText() }
    }

    fun remove(path: String): Boolean {
        val realPath = if (!path.startsWith("/")) {
            "${CoreHelper.sdcardPath}/skyline/${path}"
        } else path

        val file = File(realPath)
        if (file.isDirectory) throw RuntimeException("$path is directory use instead of \"rmdir\"")

        file.delete()
        return true
    }

    fun rmdir(path: String): Boolean {
        val realPath = if (!path.startsWith("/")) {
            "${CoreHelper.sdcardPath}/skyline/${path}"
        } else path

        val file = File(realPath)
        if (!file.isDirectory) throw RuntimeException("$path is file use instead of \"remove\"")

        file.delete()
        return true
    }
    
    fun exists(path: String): Boolean {
        val realPath = if (!path.startsWith("/")) {
            "${CoreHelper.sdcardPath}/skyline/${path}"
        } else path
        return File(realPath).exists()
    }

    private fun saveString(text: String, filename: String, append: Boolean) {
        try {
            val bw = BufferedWriter(FileWriter(filename, append))
            bw.write(text)
            bw.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}