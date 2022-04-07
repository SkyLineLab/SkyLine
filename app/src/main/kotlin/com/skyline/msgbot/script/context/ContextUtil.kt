/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.script.context

import com.naijun.graaldalvik.AndroidClassLoaderFactory
import com.skyline.msgbot.core.CoreHelper
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.PolyglotAccess
import org.graalvm.polyglot.io.FileSystem

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
            .option("js.commonjs-global-properties", "/data/user/0/com.skyline.msgbot/files/global.js")
            .option("js.commonjs-core-modules-replacements", """buffer:/data/user/0/com.skyline.msgbot/files/node_modules/buffer
            """.trimMargin())
            .option("js.foreign-object-prototype", "true")
            .option("js.syntax-extensions", "true")
            .option("js.nashorn-compat", "true")
            .option("js.ecmascript-version", "2022")
            .option("js.intl-402", "true")
            .allowCreateThread(true)
            .allowCreateProcess(true)
            .allowNativeAccess(true)
            .allowHostClassLoading(true)
            .fileSystem(FileSystem.newDefaultFileSystem())
            .allowHostClassLookup { true }.build()
    }

    fun getPythonContext(): Context {
        throw UnsupportedOperationException("GraalPython not support yet")
    }
}