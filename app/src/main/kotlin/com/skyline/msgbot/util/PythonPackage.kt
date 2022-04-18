/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
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