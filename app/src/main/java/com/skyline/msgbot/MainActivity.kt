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
import com.skyline.msgbot.bot.runtime.RuntimeManager
import com.skyline.msgbot.utils.PermissionUtil
import com.skyline.msgbot.ui.theme.SkyLineTheme
import com.skyline.msgbot.utils.ForegroundTask

class MainActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()
        PermissionUtil.isAllowPermision(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkyLineTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        if(PermissionUtil.isAllowPermision(this)) {
            if (!RuntimeManager.hasRuntime("test")) {
                RuntimeManager.addRuntime("test")
            }
            ForegroundTask.startForeground(this)
        }
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