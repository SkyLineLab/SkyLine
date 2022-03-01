/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.ui.setup

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
import com.skyline.msgbot.ui.gothicFont
import com.skyline.msgbot.ui.theme.Purple40

@Composable
fun WelcomePage() {
    androidx.compose.material3.Surface {
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
                    verticalArrangement = Arrangement.Top,
                ) {
                   Spacer(modifier = Modifier.padding(16.dp))
                   Text(
                       text = "SkyLine에 오신걸 환영합니다.",
                       fontFamily = gothicFont,
                       style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center)
                   )

                    Spacer(modifier = Modifier.padding(30.dp))

                    Text(
                        text = "SkyLine을 사용하기 위해서는 일부 권한이 필요합니다.\n\"권한 받기\" 버튼을 눌러 권한을 받으세요!",
                        fontFamily = gothicFont,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.padding(140.dp))

                    OutlinedButton(onClick = {  }, border = BorderStroke(1.dp, Purple40),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Purple40
                        )) {
                        Text(text = "권한 받기")
                    }
                }
            }
        }
    }
}