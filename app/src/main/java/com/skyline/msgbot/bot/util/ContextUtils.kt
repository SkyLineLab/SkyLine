/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
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
            .option("js.commonjs-require-cwd", "${SDCardUtils.sdcardPath}/${Constants.directoryName}/")
//            .option("js.commonjs-core-modules-replacements", "timers:timers")
            .option("js.syntax-extensions", "true")
            .option("js.nashorn-compat", "true")
            .option("js.ecmascript-version", "2022")
            .allowHostClassLookup { true }.build()
    }
}