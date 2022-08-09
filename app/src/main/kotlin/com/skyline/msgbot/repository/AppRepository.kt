package com.skyline.msgbot.repository

import android.annotation.SuppressLint
import android.os.Build

object AppRepository {

    @SuppressLint("ObsoleteSdkInt")
    fun isNougatAbove(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }

}