/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.util

import android.os.Environment

object SDCardUtils {
    val sdcardPath: String = Environment.getExternalStorageDirectory().absolutePath
}