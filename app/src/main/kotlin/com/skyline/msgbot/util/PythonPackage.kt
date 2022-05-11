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

package com.skyline.msgbot.util

import com.skyline.gordjs.util.HttpRequestUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.rauschig.jarchivelib.ArchiverFactory
import java.io.File

object PythonPackage {
    fun installPython() {
        val job = CoroutineScope(Dispatchers.IO).async {
            install()
        }
        CoroutineScope(Dispatchers.IO).launch {
            val res = job.await()

            if (!File("/data/user/0/com.skyline.msgbot/files/languages/python").exists()) {
                File("/data/user/0/com.skyline.msgbot/files/languages/python").mkdirs()
            }

            val archive = ArchiverFactory.createArchiver("zip")
            archive.extract(File("/data/user/0/com.skyline.msgbot/files/libpython.zip"), File("/data/user/0/com.skyline.msgbot/files/languages/python/"))
        }
    }

    private suspend fun install() {
        HttpRequestUtil.downloadFile("https://arthic.dev/libpython.zip", "/data/user/0/com.skyline.msgbot/files/")
    }
}