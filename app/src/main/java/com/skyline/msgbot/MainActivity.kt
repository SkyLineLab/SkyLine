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
        if (!PermissionUtil.requestStoragePermissions(this)) {
            Toast.makeText(this, "파일 권한이 없습니다 :( 권한을 허용해주세요", Toast.LENGTH_LONG).show()
        }
        if (!PermissionUtil.getAccessNotification(this)) { //알림 권한 체크
            Toast.makeText(this, "알림 읽기 권한이 없습니다 :( 권한을 허용해주세요", Toast.LENGTH_LONG).show()
            PermissionUtil.requestAccessNotification(this)
        }
        if (!RuntimeManager.hasRuntime("test")) {
            RuntimeManager.addRuntime("test")
        }
        ForegroundTask.startForeground(this)
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