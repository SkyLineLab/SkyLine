package com.skyline.msgbot.core

import android.content.Context
import android.os.Environment
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import com.google.gson.Gson
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

object CoreHelper {

    var contextGetter: (() -> Context)? = null
    val allowPackageNames = arrayListOf("com.kakao.t")
    const val directoryName: String = "skyline"
    var filesDir: String = "${Environment.getDataDirectory().absolutePath}/user/0/com.skyline.msgbot/files"
    val baseNodePath: String = "${filesDir}/languages/nodejs"
    val sdcardPath: String = Environment.getExternalStorageDirectory().absolutePath
    
    val GSON = Gson()

    init {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    fun getPowerManager(): PowerManager {
        val context = contextGetter?.invoke()
        if (context == null) {
            throw RuntimeException("context is null")
        } else {
            return context.getSystemService(Context.POWER_SERVICE) as PowerManager
        }
    }

}