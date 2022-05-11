/*
 * MIT License
 *
 * Copyright (c) 2022 SkyLineLab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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