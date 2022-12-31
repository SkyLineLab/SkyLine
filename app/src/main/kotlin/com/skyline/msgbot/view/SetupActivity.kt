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

        JSEngineRepository.getGraalJSEngine().eval("js", """
function logged(value, { kind, name }) {
  if (kind === "method") {
    return function (...args) {
      console.log('starting ' + name + ' with arguments ' + args.join(", "));
      const ret = value.call(this, ...args);
      console.log(`ending ` + name);
      return ret;
    };
  }
}

class C {
  @logged
  m(arg) {}
}

new C().m(1);
        """.trimIndent())

        setContent {
            SetupUI()
        }
    }
}