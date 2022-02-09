/**
 * Created by naijun on 2022/02/09
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skyline.msgbot.bot.api.Api
import com.skyline.msgbot.ui.theme.Purple40

@Composable
fun HomePage(context: Context) {
    Box {
        androidx.compose.material3.Surface(
            color = Purple40,
            modifier = Modifier.fillMaxSize()
        ) {

        }

        androidx.compose.material3.Surface(
            color = Color.White,
            modifier = Modifier
                .height(650.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(80.dp).copy(
                topEnd = ZeroCornerSize,
                topStart = ZeroCornerSize
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = "Dev Skyline HomePage",
                    fontFamily = gothicFont,
                    style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp, bottom = 40.dp, start = 30.dp)
                ) {
                    OutlinedButton(onClick = {
                        Api.compile()
                        Toast.makeText(context, "컴파일 되었습니다.", Toast.LENGTH_SHORT).show()
                    }, border = BorderStroke(1.dp, Purple40),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Purple40
                    )) {
                        Text(text = "Compile")
                    }
                }
            }
        }
    }
}