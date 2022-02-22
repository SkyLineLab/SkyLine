/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.nodejs.utils

import com.skyline.msgbot.bot.util.SDCardUtils
import com.skyline.msgbot.setting.Constants

object NodeJSModuleScript {
    const val timers = "module.exports = {\n" +
            "    setTimeout: setTimeout,\n" +
            "    clearTimeout: clearTimeout, \n" +
            "    setInterval: setInterval,\n" +
            "    clearInterval: clearInterval,\n" +
            "    setImmediate: setImmediate,\n" +
            "    clearImmediate: clearImmediate\n" +
            "}"
    var global = "globalThis.Buffer = require('${SDCardUtils.sdcardPath}/${Constants.directoryName}/node_modules/buffer').Buffer; // buffer"
}