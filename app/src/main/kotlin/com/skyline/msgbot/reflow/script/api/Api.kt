package com.skyline.msgbot.reflow.script.api

import android.content.Context
import com.skyline.msgbot.core.CoreHelper

object Api {

    fun getContext(): Context {
        return CoreHelper.contextGetter!!.invoke()
    }

}