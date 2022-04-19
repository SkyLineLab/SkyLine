/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.core

import android.content.Context
import android.os.Environment
import com.google.gson.GsonBuilder
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * CoreHelper
 */
object CoreHelper {
    var contextGetter: (() -> Context)? = null
    val allowPackageNames: List<String> = listOf("com.kakao")
    const val directoryName: String = "skyline"
    const val baseNodePath: String = "/data/user/0/com.skyline.msgbot/files/languages/nodejs"
    val sdcardPath: String = Environment.getExternalStorageDirectory().absolutePath
    val gson = GsonBuilder().setPrettyPrinting().create()
    init {
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}