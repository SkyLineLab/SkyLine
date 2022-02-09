package com.skyline.msgbot

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skyline.msgbot.bot.Bot
import com.skyline.msgbot.bot.runtime.RuntimeManager
import com.skyline.msgbot.bot.util.ContextUtils
import com.skyline.msgbot.ui.HomePage
import com.skyline.msgbot.ui.PermissionPage
import com.skyline.msgbot.utils.PermissionUtil
import com.skyline.msgbot.ui.theme.SkyLineTheme
import com.skyline.msgbot.utils.ForegroundTask

class MainActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()
        PermissionUtil.requestAllPermision(this, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    PermissionPage() //DEBUG
                    HomePage(context = this@MainActivity)
                }
            }
        }
        ForegroundTask.startForeground(this)
        if(PermissionUtil.requestAllPermision(this, false)) {
            if (!RuntimeManager.hasRuntime("test")) {
                RuntimeManager.addRuntime("test")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ForegroundTask.release()
        Bot.destroyThread()
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