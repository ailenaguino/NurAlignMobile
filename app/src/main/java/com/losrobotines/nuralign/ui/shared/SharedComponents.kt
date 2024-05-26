package com.losrobotines.nuralign.ui.shared

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import kotlinx.coroutines.delay

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
            /*Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = Color.White,
                contentDescription = "ir atrás",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .clickable { }
            )*/
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

    @Composable
    fun CompanionTextBalloon(texts: List<String>) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .background(
                    Color.LightGray,
                    RoundedCornerShape(32.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                TypewriterText(texts = texts)
            }
            Box(modifier = Modifier.weight(0.2f)) {
                Image(
                    painterResource(id = R.drawable.robotin_bebe),
                    contentDescription = "Fondo",
                    modifier = Modifier
                        .size(85.dp)
                        .padding(end = 1.dp, start = 4.dp)
                )
            }
        }
    }


    @Composable
    fun TypewriterText(
        texts: List<String>,
    ) {
        var textIndex by remember {
            mutableStateOf(0)
        }
        var textToDisplay by remember {
            mutableStateOf("")
        }

        LaunchedEffect(
            key1 = texts,
        ) {
            while (textIndex < texts.size) {
                texts[textIndex].forEachIndexed { charIndex, _ ->
                    textToDisplay = texts[textIndex]
                        .substring(
                            startIndex = 0,
                            endIndex = charIndex + 1,
                        )
                    delay(100)
                }
                textIndex = (textIndex + 1) % texts.size
                delay(1000)
            }
        }

        Text(
            text = textToDisplay,
            fontSize = 20.sp,
            color = Color.Black
        )
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun fabCompanion(texts: List<String>) {
        var isVisible by remember {
            mutableStateOf(false)
        }
        AnimatedContent(
            targetState = isVisible,
            transitionSpec = { fadeIn() with fadeOut() },
            content = { visible ->
                if (visible) {
                    LargeFloatingActionButton(
                        onClick = { isVisible = !isVisible },
                        shape = RoundedCornerShape(50.dp),
                        containerColor = secondaryColor
                    ) {
                        Image(
                            painterResource(id = R.drawable.robotin_bebe),
                            contentDescription = "Robotín",
                            modifier = Modifier
                                .size(85.dp)
                                .padding(4.dp)
                        )
                    }
                } else {
                    LargeFloatingActionButton(
                        onClick = { isVisible = !isVisible },
                        shape = RoundedCornerShape(10.dp),
                        containerColor = Color.White
                    ) {
                        Row(modifier = Modifier.padding(4.dp)) {
                            Box(
                                modifier = Modifier
                                    .weight(0.8f)
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                TypewriterText(texts = texts)
                            }
                            Box(modifier = Modifier.weight(0.2f)) {
                                Image(
                                    painterResource(id = R.drawable.robotin_bebe),
                                    contentDescription = "Fondo",
                                    modifier = Modifier
                                        .size(85.dp)
                                        .padding(end = 1.dp, start = 4.dp)
                                )
                            }
                        }
                    }
                }
            }, label = "companion animation"
        )
    }
}

/*FloatingActionButton(
    onClick = { isVisible = !isVisible },
    shape = RoundedCornerShape(50.dp),
    containerColor = mainColor
) {
    Image(
        painterResource(id = R.drawable.robotin_bebe),
        contentDescription = "Robotín",
        modifier = Modifier
            .size(85.dp)
            .padding(4.dp)
    )
}*/