package com.skyline.msgbot.reflow.script.api

import android.content.Context
import android.os.PowerManager
import com.skyline.msgbot.core.CoreHelper

object ScriptApp {

    fun getContext(): Context {
        return CoreHelper.contextGetter!!.invoke()
    }

    fun makeWakelock(level: Int, tag: String): PowerManager.WakeLock {
        val pm = CoreHelper.getPowerManager()
        return pm.newWakeLock(level, tag)
    }

}