/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.utils

object AppUtil {
    fun getPackageVersion(packageName: String): Double {
        val fullName = ContextHelper.contextGetter!!.invoke().packageManager.getPackageInfo(packageName, 0).versionName
        println("packageName = $packageName version = $fullName")
        return fullName.substring(0, 3).toDouble()
    }
}