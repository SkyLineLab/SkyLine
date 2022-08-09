package com.skyline.msgbot.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.reflow.App
import com.skyline.msgbot.ui.theme.Purple40
import com.skyline.msgbot.ui.theme.PurpleGrey40
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
        if (!App.roadDataById("isLicenseAccept").value) {
            this.startActivity(Intent(this, SetupActivity::class.java))
        }
        // TODO
        setContent {
            SkyLineTheme {}
        }
    }
}