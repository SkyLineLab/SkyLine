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

package com.skyline.msgbot.script.api

import com.oracle.truffle.js.runtime.Errors
import com.skyline.msgbot.runtime.RuntimeManager
import org.graalvm.polyglot.Value

/**
 * 다른 프로젝트의 JSContext에 접근하여 binding 들고옴
 * @author naijun
 */
object Bridge {

    fun getGlobalByName(name: String): Value {
        return RuntimeManager.runtimes[RuntimeManager.projectNames[name]]?.getBindings("js")
            ?: throw Errors.createLinkError("Not Found Project Name: $name")
    }

    fun getGlobalById(id: Int): Value {
        return RuntimeManager.runtimes[id]?.getBindings("js")
            ?: throw Errors.createLinkError("Not Found Project ID: $id")
    }

    fun canAccessByName(name: String): Boolean {
        return RuntimeManager.hasRuntime(name)
    }

    fun canAccessById(id: Int): Boolean {
        return RuntimeManager.runtimes.containsKey(id)
    }

    override fun toString(): String {
        return "[object Bridge]"
    }

}