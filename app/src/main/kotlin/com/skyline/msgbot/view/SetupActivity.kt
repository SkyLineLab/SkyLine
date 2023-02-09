package com.skyline.msgbot.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.orhanobut.logger.Logger
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.repository.JSEngineRepository
import com.skyline.msgbot.view.compose.SetupUI
import io.adnopt.context.AdnoptContext

class SetupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoreHelper.contextGetter = {
            this
        } // set android context

        Logger.d("test")

        val context = JSEngineRepository.getGraalJSEngine()
        val value = context.eval("js", """
            function test() { console.log(this) }
        """.trimIndent())

        context.getBindings("js").getMember("test").executeVoid()

        setContent {
            SetupUI()
        }
    }
}