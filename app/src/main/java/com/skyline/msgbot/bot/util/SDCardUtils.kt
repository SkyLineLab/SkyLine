/**
 * Created by naijun on 2022/01/27
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.util

import android.os.Environment

object SDCardUtils {
    val sdcardPath = Environment.getExternalStorageDirectory().absolutePath
}