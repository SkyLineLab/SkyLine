/*
 * MIT License
 *
 * Copyright (c) 2022 SkyLineLab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

    override fun toString(): String {
        return "[object FS]"
    }
}