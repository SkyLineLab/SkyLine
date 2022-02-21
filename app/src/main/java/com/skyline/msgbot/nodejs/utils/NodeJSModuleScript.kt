/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.nodejs.utils

object NodeJSModuleScript {
    const val timers = "module.exports = {\n" +
            "    setTimeout: setTimeout,\n" +
            "    clearTimeout: clearTimeout, \n" +
            "    setInterval: setInterval,\n" +
            "    clearInterval: clearInterval,\n" +
            "    setImmediate: setImmediate,\n" +
            "    clearImmediate: clearImmediate\n" +
            "}"
}