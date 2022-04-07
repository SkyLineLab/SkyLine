package com.skyline.msgbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.orhanobut.logger.Logger
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.runtime.RuntimeManager
import com.skyline.msgbot.script.ScriptLanguage
import com.skyline.msgbot.service.ForegroundService
import com.skyline.msgbot.ui.HomePage
import com.skyline.msgbot.ui.theme.SkyLineTheme
import com.skyline.msgbot.util.PermissionUtil

class MainActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()
        CoreHelper.contextGetter = {
            this
        } // set android context
        PermissionUtil.requestAllPermision(this, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("Logger Test")
        setContent {
            SkyLineTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomePage(context = this)
                }
            }
        }
        ForegroundService.startForeground(this)
        if(PermissionUtil.requestAllPermision(this, false)) {
            if (!RuntimeManager.hasRuntime("test")) {
                RuntimeManager.addRuntime("test", ScriptLanguage.JAVASCRIPT)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ForegroundService.release()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SkyLineTheme {
        Greeting("Android")
    }
}