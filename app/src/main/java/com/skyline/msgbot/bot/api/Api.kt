/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.api

import com.skyline.msgbot.bot.runtime.RuntimeManager
import com.skyline.msgbot.bot.session.BotChannelSession
import com.skyline.msgbot.bot.util.ApiApplyUtil
import com.skyline.msgbot.bot.util.ContextUtils
import com.skyline.msgbot.bot.util.SDCardUtils
import com.skyline.msgbot.setting.Constants
import org.graalvm.polyglot.Source
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader

/**
 * Api Object
 *
 * @author naijun
 */
object Api {
    fun compile(): Boolean {
        try {
            for (runtime in RuntimeManager.runtimes) {
                println(runtime)
                runtime.value.close()
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

    fun getRootPermission(path: String): Boolean {
        return try {
            val strArr = arrayOf("su", "mount -o remount rw $path", "chmod -R 777 $path")
            val process = Runtime.getRuntime().exec(strArr)
            val os = DataOutputStream(process.outputStream)
            os.writeBytes("exit\n")
            os.flush()
            process.waitFor()
            process.exitValue() != 255
        } catch (e: IOException) {
            false
        }
    }

    fun sendRoom(room: Any, text: Any): Boolean {
        val session = BotChannelSession.getSession(room.toString()) ?: return false
        session.room.send(text.toString())
        return true
    }
}