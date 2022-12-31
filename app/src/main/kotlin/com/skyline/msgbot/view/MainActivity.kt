package com.skyline.msgbot.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.reflow.App
import com.skyline.msgbot.service.ForegroundService
import com.skyline.msgbot.ui.theme.SkyLineTheme


class MainActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()
        CoreHelper.contextGetter = {
            this
        } // set android context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoreHelper.contextGetter = {
            this
        } // set android context
        val serviceIntent = Intent(
            this,
            ForegroundService::class.java
        )
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android")
        ContextCompat.startForegroundService(this, serviceIntent)
        if (!App.roadDataById("isLicenseAccept").value) {
            this.startActivity(Intent(this, ProjectActivity::class.java))
        }

        // TODO
        setContent {
            SkyLineTheme {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val serviceIntent = Intent(
            this,
            ForegroundService::class.java
        )
        stopService(serviceIntent)
    }
}