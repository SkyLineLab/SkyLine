/**
 * Created by naijun on 2022/01/31
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skyline.msgbot.ui.theme.Purple40
import com.skyline.msgbot.R

val gothicFont = FontFamily(Font(R.font.nanum))

@Preview(showBackground = true)
@Composable
fun PermissionPage() {
    Box {
        androidx.compose.material3.Surface(color = Purple40, modifier = Modifier.fillMaxSize()) {

        }

        androidx.compose.material3.Surface(
            color = Color.White,
            modifier = Modifier
                .height(700.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(60.dp).copy(
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
                    text = "SkyLine에 오신걸 환영합니다!",
                    fontFamily = gothicFont,
                    style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp, bottom = 40.dp, start = 30.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bell_24),
                        contentDescription = "test",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )
                    
                    Spacer(modifier = Modifier.width(30.dp))

                    Column {
                        Text(
                            text = "알림 접근 권한",
                            fontFamily = gothicFont,
                            modifier = Modifier.padding(5.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "카카오톡 봇은 알림에 있는 \"답장\" 기능을 이용한거라 해당 권한이 필수적으로 필요합니다.",
                            fontFamily = gothicFont,
                            modifier = Modifier.padding(5.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}