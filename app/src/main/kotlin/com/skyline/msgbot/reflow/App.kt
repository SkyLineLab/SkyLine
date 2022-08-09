package com.skyline.msgbot.reflow

import com.orhanobut.logger.Logger
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.model.OptionDataModel

object App {

    fun roadDataById(id: String): OptionDataModel {
        return if (id === "isTermCheck") {
            OptionDataModel(false)
        } else {
            OptionDataModel(false)
        }
    }

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

}