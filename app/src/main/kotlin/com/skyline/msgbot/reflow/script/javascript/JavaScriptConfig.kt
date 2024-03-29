package com.skyline.msgbot.reflow.script.javascript

import com.skyline.msgbot.core.CoreHelper

object JavaScriptConfig {

    private const val ECMASCRIPT_VERSION = "staging"

    private const val IMPROVE_SYNTAX = "true"

    private const val INTL_ENABLED = "true"

    private const val NASHORN_COMPAT = "true"

    private const val COMMON_JS_ENABLED = "true"

    private const val TEMPORAL_ENABLED = "true"

    private const val SHADOW_REALM_ENABLED = "true"

    private const val ERROR_CAUSE_ENABLED = "true"

    private const val IMPORT_ASSERTIONS_ENABLED = "true"

    private const val NEW_SET_METHODS_ENABLED = "true"

    private const val OPERATOR_OVERLOADING_ENABLED = "true"

    private const val FOREIGN_OBJECT_PROTOTYPE_ENABLED = "true"

    fun getDefaultContextOption(): HashMap<String, String> {
        return HashMap<String, String>().apply {
            put("js.syntax-extensions", IMPROVE_SYNTAX)
            put("js.nashorn-compat", NASHORN_COMPAT)
            put("js.ecmascript-version", ECMASCRIPT_VERSION)
            put("js.intl-402", INTL_ENABLED)
            put("js.temporal", TEMPORAL_ENABLED)
            put("js.shadow-realm", SHADOW_REALM_ENABLED)
            put("js.error-cause", ERROR_CAUSE_ENABLED)
            put("js.import-assertions", IMPORT_ASSERTIONS_ENABLED)
            put("js.new-set-methods", NEW_SET_METHODS_ENABLED)
            put("js.operator-overloading", OPERATOR_OVERLOADING_ENABLED)
            put("js.foreign-object-prototype", FOREIGN_OBJECT_PROTOTYPE_ENABLED)
        }
    }

    fun getProjectContextOption(projectName: String): HashMap<String, String> {
        return getDefaultContextOption().apply {
            put("js.commonjs-require", COMMON_JS_ENABLED)
            put("js.commonjs-require-cwd", "${CoreHelper.sdcardPath}/${CoreHelper.directoryName}/Projects/$projectName/")
            put(
                "js.commonjs-core-modules-replacements",
                "buffer:${CoreHelper.baseNodePath}/buffer,string_decoder:${CoreHelper.baseNodePath}/string_decoder,crypto:${CoreHelper.baseNodePath}/crypto,stream:${CoreHelper.baseNodePath}/stream,events:${CoreHelper.baseNodePath}/events,util:${CoreHelper.baseNodePath}/util,process:${CoreHelper.baseNodePath}/process,assert:${CoreHelper.baseNodePath}/assert,timers:${CoreHelper.baseNodePath}/timers,kapi/util/device:${CoreHelper.baseNodePath}/kapi/util/device"
            )
        }
    }

}