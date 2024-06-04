package com.losrobotines.nuralign.feature_home.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Mood
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

class FirstTimeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurAlignTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FirstTimeScreen()
                }
            }
        }
    }
}


@Preview
@Composable
private fun FirstTimeScreen() {

    var isFirstTimeVisible by remember { mutableStateOf(true) }

    AnimatedContent(
        targetState = isFirstTimeVisible,
        modifier = Modifier.fillMaxSize(),
        transitionSpec = { slideInHorizontally { it } togetherWith slideOutHorizontally (targetOffsetX = { -it }) },
        content = {it ->
            if(it){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(mainColor)
                        .wrapContentSize(Alignment.Center)
                ) {
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
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .weight(0.6f)
                        ) {
                            SharedComponents().TypewriterText(
                                texts = listOf(
                                    "¡Hola, gracias por instalar NurAlign! Yo soy Robotín \uD83E\uDD16",
                                    "Vamos a dar un tour por mi casa (la aplicación \uD83D\uDE0B), acompañame."
                                ), 70
                            )
                        }
                        Button(
                            onClick = { isFirstTimeVisible = false },
                            colors = ButtonDefaults.buttonColors(containerColor = secondaryColor),
                            modifier = Modifier
                                .weight(0.4f)
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "Seguir a Robotín")
                        }
                    }
                }
            } else {
                Tutorial()
            }
        }, label = ""
    )
}

@Preview
@Composable
fun Tutorial() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainColor).wrapContentSize(Alignment.Center)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .clip(shape = CircleShape)
                    .background(secondaryColor)
                    .size(70.dp)
                    .wrapContentSize(Alignment.Center)
            ) {
                Image(
                    painterResource(id = R.drawable.robotin_bebe_contento),
                    contentDescription = "Robotín",
                    modifier = Modifier
                        .size(70.dp)
                        .padding(4.dp)
                )
            }
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "En la pantalla de 'Inicio' vas a ver íconos como este para ir a distintas partes de la aplicación.",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardToShowFirst()
            ButtonOk()
        }
    }
}

@Composable
fun CardToShowFirst() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .size(width = 170.dp, height = 200.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.TwoTone.Mood,
                contentDescription = "Seguimiento del ánimo",
                tint = secondaryColor,
                modifier = Modifier
                    .size(90.dp)
                    .padding(8.dp)
            )
            Text(
                text = "Estados de ánimo",
                color = secondaryColor,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun ButtonOk() {
    val context = LocalContext.current
    Button(onClick = { context.startActivity(Intent(context, TutorialActivity::class.java)) }, colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)) {
        Text(text = "¡Ok! Entendí, sigamos.", fontSize = 18.sp)
    }
}