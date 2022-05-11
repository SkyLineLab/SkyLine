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

package com.skyline.msgbot.runtime

import com.orhanobut.logger.Logger
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.project.ProjectInitUtil
import com.skyline.msgbot.script.ScriptLanguage
import com.skyline.msgbot.script.api.util.ApiApplyUtil
import com.skyline.msgbot.script.client.BotClient
import com.skyline.msgbot.script.context.ContextUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source
import java.io.File
import java.util.Stack

internal object RuntimeManager {

    val runtimes: HashMap<Int, Context> = hashMapOf()
    val clients: HashMap<Int, BotClient> = hashMapOf()
    val powerMap: HashMap<Int, Boolean> = hashMapOf()
    val projectIds: HashMap<Int, String> = hashMapOf()
    val projectNames: HashMap<String, Int> = hashMapOf()
    val projectLanguages: HashMap<Int, ScriptLanguage> = hashMapOf()

    private val runtimeCount: Stack<Any> = Stack()

    /**
     * 리턴 타입은 Boolean인데 코루틴 내부라 false를 반환할수 없어 무조건 true만 반환
     * 다만 만들기에 실패하면 로거에 뜸
     */
    fun addRuntime(projectName: String, language: ScriptLanguage): Boolean {
        CoroutineScope(Dispatchers.Main).launch {
            ProjectInitUtil.createProject(projectName, language)
            ProjectInitUtil.installNodePackage()
            ProjectInitUtil.makeModuleDir(projectName)
            if (!ProjectInitUtil.isExistsProject(projectName)) {
                Logger.e("Create Project Error")
            } else {
                Logger.d("add")
                val id = runtimeCount.size + 1
                Logger.d("projectName = $projectName id = $id")
                val context = ContextUtil.getJSContext(projectName)
                runtimeCount.push(0)
                val global = context.getBindings("js")
                ApiApplyUtil.apply(global, true, id)
                ApiApplyUtil.installNodejs(context = context)
                runtimes[id] = context
                powerMap[id] = true
                clients[id] = BotClient()
                projectIds[id] = projectName
                projectNames[projectName] = id
                when (language) {
                    ScriptLanguage.JAVASCRIPT -> {
                        projectLanguages[id] = language
                        context.eval(
                            Source.newBuilder(
                                "js",
                                File("${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/Projects/$projectName/script.js")
                            ).mimeType("application/javascript+module").build()
                        )
                    }

                    else -> throw UnsupportedOperationException("Not Support Language: $language")
                }
            }
        }
        return true
    }

    fun hasRuntime(projectName: String): Boolean {
        return runtimes[projectNames[projectName]] != null
    }

    fun getIsPowerOn(number: Number): Boolean {
        val boolean: Boolean = if (powerMap[number] == null) {
            false
        } else {
            powerMap[number] ?: false
        }

        return boolean
    }

    fun compile(project: String): Boolean {
        return if (projectNames[project] == null) false
        else {
            runtimes[projectNames[project]]?.eval(
                Source.newBuilder(
                    "js",
                    File("${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/Projects/$project/script.js")
                ).mimeType("application/javascript+module").build()
            )
            true
        }
    }

}