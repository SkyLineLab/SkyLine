package com.skyline.msgbot.view.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skyline.msgbot.ui.theme.*

val dhFont = FontFamily(
    Font(com.skyline.msgbot.R.font.dh, FontWeight.Normal),
)

@Preview(showBackground = true)
@Composable
fun SetupUI() {
    SkyLineTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

            Box {
                val color = if (isSystemInDarkTheme()) {
                    Purple100
                } else Purple40

                val textColor = if (isSystemInDarkTheme()) {
                    White
                } else Black
                Surface(
                    color = color,
                    modifier = Modifier.fillMaxSize()
                ) {

                }

                Surface(
                    modifier = Modifier
                        .height(650.dp)
                        .fillMaxHeight()
                        .fillMaxWidth(),
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
                            text = "SkyLine에 오신걸 환영합니다!",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                textAlign = TextAlign.Center,
                                color = textColor
                            ),
                            fontFamily = dhFont,
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            text = "앱 이용약관을 동의 해주셔야 앱을 사용할 수 있습니다.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textAlign = TextAlign.Center,
                                color = textColor
                            ),
                            fontFamily = dhFont,
                        )

                        Spacer(modifier = Modifier.padding(200.dp))

                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Snackbar {
                                Text(text = "앱 이용약관을 동의 해주셔야 앱을 사용할 수 있습니다.")
                            }
                        }
                    }
                }
            }
        }
    }
}