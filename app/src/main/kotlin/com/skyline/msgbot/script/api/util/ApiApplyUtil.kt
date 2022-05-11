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

package com.skyline.msgbot.script.api.util

import android.annotation.SuppressLint
import com.skyline.gordjs.GordJS
import com.skyline.msgbot.project.BotProject
import com.skyline.msgbot.script.api.Api
import com.skyline.msgbot.script.api.Bridge
import com.skyline.msgbot.script.api.Event
import com.skyline.msgbot.script.api.FileStream
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Value

object ApiApplyUtil {
    fun apply(value: Value, isProject: Boolean, runtimeId: Int) {
        value.putMember("FileStream", FileStream)
        value.putMember("Api", Api)
        value.putMember("Bridge", Bridge)
        value.putMember("Event", Event::class.java)

        if (isProject) {
            value.putMember("BotProject", BotProject(runtimeId))
        }

        GordJS.applyContext(value) // init nodejs
    }

    @SuppressLint("SdCardPath")
    fun installNodejs(context: Context) {
        val source = FileStream.read("/data/user/0/com.skyline.msgbot/files/global.js")
        context.eval("js", source)
    }
}