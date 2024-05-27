package com.losrobotines.nuralign.ui.shared

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.draw.clip
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
                TypewriterText(texts = texts, 100)
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
        texts: List<String>, delayValue: Int
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
                    delay(delayValue.toLong())
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
                                TypewriterText(texts = texts, 100)
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

    @Composable
    fun companionCongratulation(isVisible:Boolean) {
        AnimatedVisibility(visible = isVisible, enter = scaleIn(tween(1000))) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mainColor)
                    .wrapContentSize(Alignment.Center)
            ) {
                AnimatedVisibility(visible = isVisible, enter = scaleIn(spring(200F))) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .clip(shape = CircleShape)
                            .background(secondaryColor)
                            .size(300.dp)
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Image(
                            painterResource(id = R.drawable.robotin_bebe_contento),
                            contentDescription = "Robotín",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(4.dp)
                        )
                    }
                }
                AnimatedVisibility(visible = isVisible, enter = scaleIn(spring(300F))) {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        modifier = Modifier
                            .height(150.dp)
                            .width(300.dp)
                            .align(Alignment.CenterHorizontally)
                            .wrapContentSize(Alignment.Center),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Box(modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .weight(0.6f)) {
                            TypewriterText(
                                texts = listOf(
                                    "\uD83C\uDF89 Felicitaciones \uD83C\uDF89 Completaste el seguimiento",
                                    "¿Vamos al siguiente? ¡Vos podés! \uD83D\uDCAA"
                                ), 50
                            )
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(containerColor = secondaryColor),
                            modifier = Modifier
                                .weight(0.4f)
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "Ir al próximo seguimiento")
                        }
                    }
                }
            }
        }
    }
}
