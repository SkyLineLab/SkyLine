/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.util

import com.skyline.msgbot.script.context.ContextUtil
import org.graalvm.polyglot.Value

object JSGlobal {
    val global = createGlobal()

    private fun createGlobal(): Value {
        val context = ContextUtil.getJSContext()
        return context.getBindings("js")
    }
}