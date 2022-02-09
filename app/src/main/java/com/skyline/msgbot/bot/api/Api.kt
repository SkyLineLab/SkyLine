/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.api

import com.skyline.msgbot.bot.runtime.RuntimeManager
import com.skyline.msgbot.bot.util.ApiApplyUtil
import com.skyline.msgbot.bot.util.ContextUtils
import com.skyline.msgbot.bot.util.SDCardUtils
import com.skyline.msgbot.setting.Constants
import org.graalvm.polyglot.Source
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader

object Api {
    fun compile(): Boolean {
        try {
            for (runtime in RuntimeManager.runtimes) {
                val cx = ContextUtils.getJSContext()
                ApiApplyUtil.applyBotApi(cx.getBindings("js"), true, runtime.key)
                RuntimeManager.runtimes[runtime.key] = cx
                RuntimeManager.runtimes[runtime.key]?.eval(
                    Source.create(
                        "js",
                        FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/${RuntimeManager.projectIds[runtime.key]}/script.js")
                    )
                )
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun getRootPermission(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(process.outputStream)
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            os.writeBytes("exit\n")
            os.flush()
            process.waitFor()
            process.exitValue() != 255
        } catch (e: IOException) {
            false
        }
    }
}