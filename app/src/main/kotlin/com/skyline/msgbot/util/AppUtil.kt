/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.util

import android.os.Build
import com.orhanobut.logger.Logger
import com.skyline.msgbot.core.CoreHelper

object AppUtil {
    fun getPackageVersion(packageName: String): Double {
        val context = CoreHelper.contextGetter?.invoke()
        return if (context == null) {
            Logger.e("Android Context is null")
            9.0
        } else {
            val fullName = context.packageManager.getPackageInfo(packageName, 0).versionName
            Logger.d("packageName = %s version = %s", packageName, fullName)
            fullName.substring(0, 3).toDouble()
        }
    }
    
    fun isNougatAbove(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }
}