package com.losrobotines.nuralign.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

class SharedComponents {

    @Composable
    fun HalfCircleTop(title: String) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
            alignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            Alignment.TopCenter
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = Color.White,
                contentDescription = "ir atrás",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .clickable { }
            )
            Text(
                text = title,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 43.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }

    @Composable
    fun SelectDayButtons() {
        val days = arrayOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do")
        Row(horizontalArrangement = Arrangement.Center) {
            for (day in days) {
                Text(
                    text = day,
                    color = secondaryColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1.4f)
                )
            }
        }

        Row(horizontalArrangement = Arrangement.Center) {
            for (day in days) {
                var day by remember { mutableStateOf(false) }
                Button(
                    onClick = { day = !day },
                    shape = CutCornerShape(10),
                    colors = ButtonDefaults.buttonColors(containerColor = if (day) secondaryColor else mainColor),
                    modifier = Modifier
                        .weight(1.4f)
                        .height(24.dp)
                        .padding(horizontal = 1.dp)
                        .defaultMinSize(12.dp)

                ) {
                    Text(text = " ")
                }
            }
        }
    }
}