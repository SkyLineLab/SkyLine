/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.script.api

import com.skyline.msgbot.core.CoreHelper
import java.io.*

/**
 * 일단은 레거시 호환성을 위해 스크립트에도 넣음
 */
object FileStream {
    fun read(name: String): String? {
        return try {
            val file: String = if (name.startsWith("/")) name
            else "${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/$name"
            val str = loadString(file)
            str
        } catch (e: Exception) {
            null
        }
    }

    fun write(name: String, value: String): Boolean {
        return try {
            val file: String = if (name.startsWith("/")) name
            else "${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/$name"
            saveString(value, file, false)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun append(name: String, value: String): Boolean {
        return try {
            val file: String = if (name.startsWith("/")) name
            else "${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/$name"
            saveString(value, file, true)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun remove(name: String): Boolean {
        return try {
            val file: File = if (name.startsWith("/")) File(name)
            else File("${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/$name")
            if (file.exists()) file.delete()
            true
        } catch (e: Exception) {
            false
        }
    }

    //외부 저장소에 데이터 저장
    private fun saveString(text: String, filename: String, append: Boolean) {
        try {
            val bw = BufferedWriter(FileWriter(filename, append))
            bw.write(text)
            bw.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    //외부 저장소에서 데이터 가져옴
    private fun loadString(filename: String): String {
        return try {
            val br = BufferedReader(FileReader(filename))
            var readStr = ""
            var str: String?
            while (br.readLine().also { str = it } != null)
                readStr += "$str\n"
            br.close()
            readStr
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            e.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            e.toString()
        }
    }
}