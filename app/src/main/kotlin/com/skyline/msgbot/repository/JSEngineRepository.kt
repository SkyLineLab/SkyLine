package com.skyline.msgbot.repository

import com.caoccao.javet.interop.V8Host
import com.caoccao.javet.interop.V8Runtime
import com.naijun.graaldalvik.AndroidClassLoaderFactory
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.core.CoreHelper.baseNodePath
import com.skyline.msgbot.reflow.script.javascript.JavaScriptConfig
import io.adnopt.context.AdnoptContext
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.PolyglotAccess

object JSEngineRepository {

    /**
     * GraalJS + adnopt(develop)
     *
     * @return {org.graalvm.polyglot.Context}
     */
    fun getGraalJSEngine(): Context {
        return Context.newBuilder("js")
            .allowHostAccess(HostAccess.ALL)
            .allowPolyglotAccess(PolyglotAccess.ALL)
//            .hostClassLoader(AndroidClassLoaderFactory.createClassLoader(
//                CoreHelper.contextGetter!!.invoke()
//            ))
            .allowExperimentalOptions(true)
            .allowIO(true)
            .options(
                JavaScriptConfig.getDefaultContextOption()
            )
            .allowCreateThread(true)
            .allowCreateProcess(true)
            .allowNativeAccess(true)
            .allowHostClassLoading(true)
            .allowHostClassLookup { true }.build()
    }

    fun getGraalJSEngine(projectName: String): Context {
        return Context.newBuilder("js")
            .allowHostAccess(HostAccess.ALL)
            .allowPolyglotAccess(PolyglotAccess.ALL)
//            .hostClassLoader(AndroidClassLoaderFactory.createClassLoader(CoreHelper.contextGetter!!.invoke()))
            .allowExperimentalOptions(true)
            .allowIO(true)
            .options(
                JavaScriptConfig.getProjectContextOption(projectName)
            )
            .allowCreateThread(true)
            .allowCreateProcess(true)
            .allowNativeAccess(true)
            .allowHostClassLoading(true)
            .allowHostClassLookup { true }.build()
    }

    /**
     * GraalJS + Adnopt (nodejs support)
     */
    fun getAdnoptJSEngine(projectName: String): AdnoptContext {
        return getGraalJSEngine(projectName).let {
            AdnoptContext.create(it)
        }
    }

    /**
     * Javet
     */
    fun getV8JSEngine(): V8Runtime {
        return V8Host.getV8Instance().createV8Runtime()
    }

    /**
     * Javet
     */
    fun getNodeJSEngine(): V8Runtime {
        return V8Host.getNodeInstance().createV8Runtime()
    }

}