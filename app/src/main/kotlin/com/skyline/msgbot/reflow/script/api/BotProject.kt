package com.skyline.msgbot.reflow.script.api

import com.caoccao.javet.interop.V8Runtime
import com.skyline.msgbot.reflow.project.Project
import com.skyline.msgbot.reflow.project.ProjectManager
import com.skyline.msgbot.reflow.project.loader.ProjectLoader
import com.skyline.msgbot.reflow.script.JSEngineType
import com.skyline.msgbot.reflow.script.client.BotClient
import org.graalvm.polyglot.proxy.ProxyArray

class BotProject(
    private val project: Project,
    private val client: BotClient
) {

    /**
     * 해당 프로젝트의 BotClient를 반환합니다.
     * @return BotClient
     */
    fun getClient(): BotClient {
        return client
    }

    /**
     * 모든 프로젝트 이름을 들고 옵니다.
     * @return string[]
     */
    fun getAllProjectNames(): Any {
        return when (project.type) {
            JSEngineType.GRAALVM_JS -> {
                ProxyArray.fromList(ProjectManager.list.keys.toList())
            }

            JSEngineType.V8 -> {
                val arr = project.getEngineContext<V8Runtime>().createV8ValueArray()
                ProjectManager.list.forEach { (name, project) ->
                    arr.push(name)
                }
                arr
            }
        }
    }

    /**
     * 해당 프로젝트의 엔진 Context를 반환 합니다.
     * @return Context
     */
    fun getScriptContext(): Any {
        return project.getEngineContext()
    }

    /**
     * 해당 프로젝트의 전원을 킵니다.
     * @return boolean
     */
    fun on() {
        TODO("Not Implement Yet")
    }

    /**
     * 해당 프로젝트의 전원을 끕니다.
     * @return boolean
     */
    fun off() {
        TODO("Not Implement Yet")
    }

    /**
     * 해당 프로젝트를 재 컴파일합니다.
     * @return boolean
     */
    fun compile() {

    }

}