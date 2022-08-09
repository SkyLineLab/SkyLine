package com.skyline.msgbot.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.orhanobut.logger.Logger
import com.skyline.msgbot.repository.JSEngineRepository
import com.skyline.msgbot.view.compose.SetupUI
import io.adnopt.context.AdnoptContext

class SetupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetupUI()
        }
    }
}