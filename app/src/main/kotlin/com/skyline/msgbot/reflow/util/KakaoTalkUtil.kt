package com.skyline.msgbot.reflow.util

import android.os.Build
import androidx.core.content.pm.PackageInfoCompat
import com.skyline.msgbot.core.CoreHelper

object KakaoTalkUtil {

    fun isNewStruct(packageName: String): Boolean {
        return PackageInfoCompat.getLongVersionCode(
            CoreHelper.contextGetter!!.invoke().packageManager.getPackageInfo(packageName, 0)
        ) >= 2309700 && Build.VERSION.SDK_INT >= 30
    }

}