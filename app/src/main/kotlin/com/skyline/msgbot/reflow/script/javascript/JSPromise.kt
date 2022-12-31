package com.skyline.msgbot.reflow.script.javascript

import com.caoccao.javet.interop.V8Runtime
import com.skyline.msgbot.reflow.project.Project
import com.skyline.msgbot.reflow.script.JSEngineType
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Value
import org.graalvm.polyglot.proxy.ProxyExecutable
import java.util.concurrent.CompletableFuture

object JSPromise {

    fun createPromiseObject(project: Project, future: CompletableFuture<Any>): Any {
        if (!isPromiseEnvironment(project.type)) throw RuntimeException("Promise not support this environment")

        return when (project.type) {
            JSEngineType.GRAALVM_JS -> {
                val binding = project.getEngineContext<Context>().getBindings("js")
                val promise = binding.getMember("Promise")
                promise.newInstance(ProxyExecutable { arguments: Array<Value> ->
                    val resolve = arguments[0]
                    val reject = arguments[1]
                    future.whenComplete { result, ex ->
                        if (result != null) {
                            resolve.execute(result)
                        } else {
                            reject.execute(ex)
                        }
                    }
                    null
                })
            }

            JSEngineType.V8 -> {
                val promise = project.getEngineContext<V8Runtime>().createV8ValuePromise()

                future.whenComplete { result, ex ->
                    if (result != null) {
                        promise.resolve(result)
                    } else {
                        promise.reject(ex)
                    }
                }

                promise
            }
        }
    }

    private fun isPromiseEnvironment(type: JSEngineType): Boolean {
        return type == JSEngineType.GRAALVM_JS || type == JSEngineType.V8
    }

}