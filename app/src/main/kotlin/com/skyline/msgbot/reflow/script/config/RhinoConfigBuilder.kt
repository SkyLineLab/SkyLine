package com.skyline.msgbot.reflow.script.config

import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable
import kotlin.properties.Delegates

class RhinoConfigBuilder {

    val context: Context by Delegates.notNull()

    val scope: Scriptable by Delegates.notNull()

}