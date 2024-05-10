package com.losrobotines.nuralign.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.R

class SharedComponents {

    @Composable
    fun HalfCircleTop(title:String) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
            alignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth()
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
                    .clickable { /*TODO*/ }
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
}