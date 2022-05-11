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
import com.skyline.msgbot.script.api.Api
import com.skyline.msgbot.ui.theme.Purple40

// val gothicFont = FontFamily(Font(R.font.nanum))

@Composable
fun HomePage(context: Context) {

    Box {
        Surface(
            color = Purple40,
            modifier = Modifier.fillMaxSize()
        ) {

        }

        Surface(
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
                    style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center, color = Color.Black)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp, bottom = 40.dp, start = 30.dp)
                ) {
                    OutlinedButton(onClick = {
                        Api.compileAll()
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