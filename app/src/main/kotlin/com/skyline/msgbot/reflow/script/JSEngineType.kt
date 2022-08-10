package com.skyline.msgbot.reflow.script

enum class JSEngineType {
    GRAALVM_JS, // Recommend
    V8,
    @Deprecated(message = "RhinoJS is more faster than GraalJS, But it does not Support ES6 Perfectly")
    RHINO,
}