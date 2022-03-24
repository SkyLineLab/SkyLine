/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
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