/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.utils

import android.content.Context

/**
 * Context Helper
 * @author naijun
 */
object ContextHelper {
    var contextGetter: (() -> Context)? = null
}