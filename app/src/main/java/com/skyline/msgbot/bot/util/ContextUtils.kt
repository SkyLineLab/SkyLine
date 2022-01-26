/**
 * Created by naijun on 2022/01/27
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.util

import com.skyline.msgbot.setting.Constants
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess

object ContextUtils {
    fun getJSContext(): Context {
        return Context.newBuilder("js")
            .allowHostAccess(HostAccess.ALL)
            .allowExperimentalOptions(true)
            .allowIO(true)
            .option("js.commonjs-require", "true")
            .option("js.commonjs-require-cwd", "${SDCardUtils.sdcardPath}/${Constants.directoryName}/modules")
            .option("js.syntax-extensions", "true")
            .option("js.nashorn-compat", "true")
            .option("js.ecmascript-version", "2022")
            .allowHostClassLookup { true }.build()
    }
}