package com.skyline.msgbot.reflow.script

enum class JSEngineType {
    GRAALVM_JS, // Recommend, Java Interop, ES2023
    V8, // ES2023, No Java Interop
}