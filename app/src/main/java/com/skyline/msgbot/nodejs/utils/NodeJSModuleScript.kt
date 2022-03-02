/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.nodejs.utils

import com.skyline.msgbot.utils.SDCardUtils
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

    fun getGlobalScript(projectName: String): String {
        return "globalThis.Buffer = require('${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$projectName/node_modules/buffer').Buffer; // buffer\n" +
                "globalThis.process = require('${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$projectName/node_modules/process'); // process"
    }
}