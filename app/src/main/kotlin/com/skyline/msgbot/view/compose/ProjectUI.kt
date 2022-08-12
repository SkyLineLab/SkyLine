package com.skyline.msgbot.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skyline.msgbot.ui.theme.*

@Preview(showBackground = true)
@Composable
fun ProjectUI() {
    SkyLineTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = White) {
        }
        Row(){

        }
        Button(modifier = Modifier
            .offset(x=300.dp, y=700.dp)
            .size(80.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(contentColor = White),
            onClick = {
                //TODO
            }) {
            Text("+", fontSize = 35.sp, modifier = Modifier.align(Alignment.Top))
        }
    }
}