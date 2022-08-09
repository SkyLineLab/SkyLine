package com.skyline.msgbot.repository

import com.caoccao.javet.interop.V8Host
import com.caoccao.javet.interop.V8Runtime
import com.naijun.graaldalvik.AndroidClassLoaderFactory
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.core.CoreHelper.baseNodePath
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
            .hostClassLoader(AndroidClassLoaderFactory.createClassLoader(
                CoreHelper.contextGetter!!.invoke()
            ))
            .allowExperimentalOptions(true)
            .allowIO(true)
            .option("js.foreign-object-prototype", "true")
            .option("js.syntax-extensions", "true")
            .option("js.nashorn-compat", "true")
            .option("js.ecmascript-version", "2022")
            .option("js.intl-402", "true")
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
            .hostClassLoader(AndroidClassLoaderFactory.createClassLoader(CoreHelper.contextGetter!!.invoke()))
            .allowExperimentalOptions(true)
            .allowIO(true)
            .option("js.commonjs-require", "true")
            .option("js.commonjs-require-cwd", "${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/Projects/$projectName")
            .option(
                "js.commonjs-core-modules-replacements",
                "buffer:$baseNodePath/buffer,string_decoder:$baseNodePath/string_decoder,crypto:$baseNodePath/crypto,stream:$baseNodePath/stream,events:$baseNodePath/events,util:$baseNodePath/util,process:$baseNodePath/process,assert:$baseNodePath/assert,timers:$baseNodePath/timers,kapi/util/device:$baseNodePath/kapi/util/device"
            )
            .option("js.foreign-object-prototype", "true")
            .option("js.syntax-extensions", "true")
            .option("js.nashorn-compat", "true")
            .option("js.ecmascript-version", "staging")
            .option("js.intl-402", "true")
            .allowCreateThread(true)
            .allowCreateProcess(true)
            .allowNativeAccess(true)
            .allowHostClassLoading(true)
            .allowHostClassLookup { true }.build()
    }

    /**
     * @deprecated
     */
    @Deprecated(message = "RhinoJS is more faster than GraalJS, But it's not Support ES6 Perfectly")
    fun getRhinoJSEngine(): org.mozilla.javascript.Context {
        val context = org.mozilla.javascript.Context.enter()
        context.languageVersion = org.mozilla.javascript.Context.VERSION_ES6
        return context
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