/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.script.api

import android.content.Context
import com.orhanobut.logger.Logger
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.runtime.RuntimeManager
import com.skyline.msgbot.script.ScriptLanguage
import com.skyline.msgbot.script.api.util.ApiApplyUtil
import com.skyline.msgbot.script.context.ContextUtil
import com.skyline.msgbot.session.ChannelSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.graalvm.polyglot.Source
import java.io.File

/**
 * legacy Api
 */
object Api {
    fun getContext(): Context? {
        return CoreHelper.contextGetter?.invoke()
    }

    fun compileAll(): Boolean {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                for (runtime in RuntimeManager.runtimes) {
                    val ctx = ContextUtil.getJSContext(RuntimeManager.projectIds[runtime.key] ?: "")
                    val global = ctx.getBindings("js")
                    ApiApplyUtil.apply(global, true, runtime.key)
                    ApiApplyUtil.installNodejs(context = ctx)
                    RuntimeManager.runtimes[runtime.key] = ctx
                    when (RuntimeManager.projectLanguages[runtime.key]) {
                        ScriptLanguage.JAVASCRIPT -> {
                            RuntimeManager.runtimes[runtime.key]?.eval(
                                Source.newBuilder(
                                    "js",
                                    File("${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/Projects/${RuntimeManager.projectIds[runtime.key]}/script.js")
                                ).mimeType("application/javascript+module").build()
                            )
                        }

                        else -> throw UnsupportedOperationException("Not Support Language")
                    }
                }
            }

            return true
        } catch (e: Exception) {
            Logger.e("Compile: ${e.message} stackTrace = ${e.stackTraceToString()}")
            return false
        }
    }

    fun canReply(room: String): Boolean {
        return ChannelSession.hasSession(room)
    }

    override fun toString(): String {
        return "[object Api]"
    }
}