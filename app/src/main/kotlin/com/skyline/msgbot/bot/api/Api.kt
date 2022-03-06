/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.api

import android.content.Context
import com.skyline.msgbot.bot.runtime.RuntimeManager
import com.skyline.msgbot.bot.script.ScriptLanguage
import com.skyline.msgbot.bot.script.TypeScript
import com.skyline.msgbot.bot.session.BotChannelSession
import com.skyline.msgbot.bot.util.ApiApplyUtil
import com.skyline.msgbot.bot.util.ContextUtils
import com.skyline.msgbot.utils.SDCardUtils
import com.skyline.msgbot.setting.Constants
import com.skyline.msgbot.utils.ContextHelper
import org.graalvm.polyglot.Source
import org.graalvm.polyglot.Value
import java.io.DataOutputStream
import java.io.IOException

/**
 * Api Object
 *
 * @author naijun
 */
object Api {
    fun compileAll(): Boolean {
        try {
            Thread {
                for (runtime in RuntimeManager.runtimes) {
                    println(runtime)
                    val cx = ContextUtils.getJSContext(RuntimeManager.projectIds[runtime.key]!!)
                    ApiApplyUtil.applyBotApi(cx.getBindings("js"), true, runtime.key)
                    RuntimeManager.runtimes[runtime.key] = cx
                    when (RuntimeManager.projectLanguage[runtime.key]) {
                        ScriptLanguage.JAVASCRIPT -> {
                            RuntimeManager.runtimes[runtime.key]?.eval(
                                Source.create(
                                    "js",
                                    FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/${RuntimeManager.projectIds[runtime.key]}/script.js")
                                )
                            )
                        }

                        ScriptLanguage.TYPESCRIPT -> {
                            val originalScript = FileStream.read("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/${RuntimeManager.projectIds[runtime.key]}/script.ts") ?: ""
                            val convertedScript = TypeScript.convertScript(originalScript)
                            RuntimeManager.runtimes[runtime.key]?.eval(
                                Source.create(
                                    "js",
                                    convertedScript
                                )
                            )
                        }

                        else -> {
                            // DO NOT ANYTHING
                        }
                    }
                }
            }.start()

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

    fun sendRoom(room: Any, text: Value?): Boolean {
        val session = BotChannelSession.getSession(room.toString()) ?: return false
        session.room.send(text)
        return true
    }

    fun getContext(): Context? {
        return ContextHelper.contextGetter?.invoke();
    }
}