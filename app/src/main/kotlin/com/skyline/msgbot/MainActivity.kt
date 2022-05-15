/*
 * MIT License
 *
 * Copyright (c) 2022 SkyLineLab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
        if (PermissionUtil.requestAllPermision(this, false)) {
            // for ((key, value) in RuntimeManager.runtimes) {
            //     if (RuntimeManager.hasRuntime(i.value)) {
            //         RuntimeManager.addRuntime(i.value, ScriptLanguage.JAVASCRIPT)
            //     }
            // }

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