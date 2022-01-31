package com.skyline.msgbot

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.skyline.msgbot.bot.Bot
import com.skyline.msgbot.bot.runtime.RuntimeManager
import com.skyline.msgbot.ui.PermissionPage
import com.skyline.msgbot.utils.PermissionUtil
import com.skyline.msgbot.ui.theme.SkyLineTheme
import com.skyline.msgbot.utils.ForegroundTask

class MainActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkyLineTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
                PermissionPage()
            }
        }
        ForegroundTask.startForeground(this)
        if(PermissionUtil.isAllowPermision(this)) {
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