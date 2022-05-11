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

package com.skyline.msgbot.script.context

import com.naijun.graaldalvik.AndroidClassLoaderFactory
import com.skyline.msgbot.core.CoreHelper
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.PolyglotAccess
import com.skyline.msgbot.core.CoreHelper.baseNodePath

/**
 * GraalVM Context getter
 * @author naijun
 */
object ContextUtil {

    fun getJSContext(projectName: String): Context {
        return Context.newBuilder("js")
            .allowHostAccess(HostAccess.ALL)
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .hostClassLoader(AndroidClassLoaderFactory.createClassLoader(CoreHelper.contextGetter!!.invoke()))
            .allowExperimentalOptions(true)
            .allowIO(true)
            .option("js.commonjs-require", "true")
            .option("js.commonjs-require-cwd", "${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/Projects/$projectName")
            .option(
                "js.commonjs-core-modules-replacements",
                "buffer:$baseNodePath/buffer,string_decoder:$baseNodePath/string_decoder,crypto:$baseNodePath/crypto,stream:$baseNodePath/stream,events:$baseNodePath/events,util:$baseNodePath/util,process:$baseNodePath/process,assert:$baseNodePath/assert,timers:$baseNodePath/timers,kapi/util/device:$baseNodePath/kapi/util/device"
            )
            .option("js.foreign-object-prototype", "true")
            .option("js.syntax-extensions", "true")
            .option("js.nashorn-compat", "true")
            .option("js.ecmascript-version", "staging")
            .option("js.intl-402", "true")
            .allowCreateThread(true)
            .allowCreateProcess(true)
            .allowNativeAccess(true)
            .allowHostClassLoading(true)
            .allowHostClassLookup { true }.build()
    }

    fun getJSContext(): Context {
        return Context.newBuilder("js")
            .allowHostAccess(HostAccess.ALL)
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .hostClassLoader(AndroidClassLoaderFactory.createClassLoader(CoreHelper.contextGetter!!.invoke()))
            .allowExperimentalOptions(true)
            .allowIO(true)
            .option("js.foreign-object-prototype", "true")
            .option("js.syntax-extensions", "true")
            .option("js.nashorn-compat", "true")
            .option("js.ecmascript-version", "2022")
            .option("js.intl-402", "true")
            .allowCreateThread(true)
            .allowCreateProcess(true)
            .allowNativeAccess(true)
            .allowHostClassLoading(true)
            .allowHostClassLookup { true }.build()
    }

    fun getPythonContext(): Context {
        throw UnsupportedOperationException("GraalPython not support yet")
    }

}