package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

@Composable
fun MoodTrackerScreenComponent(navController: NavController) {
    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        item {
            SharedComponents().HalfCircleTop("Seguimiento del\nestado del ánimo")
        }
        item {
            Column(modifier = Modifier.fillMaxSize()) {
                Robotin()
                Linea()
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        item {
            AnimoDeprimido()
        }
        item {
            AnimoElevado()
        }
        item {
            AnimoIrritable()
        }
        item {
            AnimoAnsioso()
        }
        /*
        item {
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                },
                modifier = Modifier
                    .padding(start = 280.dp)
                    .padding(vertical = 36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text("Guardar")
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
         */
    }
}


@Composable
private fun AnimoDeprimido() {
    Spacer(modifier = Modifier.height(10.dp))
    val colors = listOf(
        Color(0xff9ebadc),
        Color(0xff678bb7),
        Color(0xff385f8e),
        Color(0xff1b477c),
        Color(0xff001e41)
    )
    SelectAnimo(
        "Animo más deprimido",
        modifier = Modifier.fillMaxWidth(),
        iconResId = R.drawable.animo_deprimido_additional_note_icon,
        iconContentDescription = ".",
        colors = colors
    )
}

@Composable
private fun AnimoElevado() {
    Spacer(modifier = Modifier.height(20.dp))
    val colors = listOf(
        Color(0xff91c776),
        Color(0xff67a549),
        Color(0xff408022),
        Color(0xff29630e),
        Color(0xff144000)
    )
    SelectAnimo(
        "Ánimo más elevado",
        modifier = Modifier.fillMaxWidth(),
        iconResId = R.drawable.animo_elevado_additional_note_icon,
        iconContentDescription = ".",
        colors = colors
    )
}

@Composable
private fun AnimoIrritable() {
    Spacer(modifier = Modifier.height(20.dp))
    val colors = listOf(
        Color(0xffdec278),
        Color(0xffac914b),
        Color(0xff7c6529),
        Color(0xff5f4b18),
        Color(0xff402e00)
    )
    SelectAnimo(
        "Ánimo más irritable",
        modifier = Modifier.fillMaxWidth(),
        iconResId = R.drawable.animo_irritable_additional_note_icon,
        iconContentDescription = ".",
        colors = colors
    )
}

@Composable
private fun AnimoAnsioso() {
    Spacer(modifier = Modifier.height(20.dp))
    val colores = listOf(
        Color(0xffc381ba),
        Color(0xffa05695),
        Color(0xff813675),
        Color(0xff732166),
        Color(0xff400036)
    )
    SelectAnimo(
        "Ánimo más ansioso",
        modifier = Modifier.fillMaxWidth(),
        iconResId = R.drawable.animo_ansioso_additional_note_icon,
        iconContentDescription = ".",
        colors = colores
    )
}

@Composable
fun Linea() {
    val lineaModifier = Modifier
        .fillMaxWidth()
        .height(4.dp)

    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Vie, 10 de Mayo",
            color = Color.Black,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 30.dp, end = 8.dp)
        )
        Box(
            modifier = lineaModifier
                .background(mainColor)
        )
    }
}

@Composable
fun SelectAnimo(
    title: String,
    modifier: Modifier,
    iconResId: Int,
    iconContentDescription: String,
    colors: List<Color>
) {
    var selectedBox by remember { mutableIntStateOf(-1) }
    val labels = listOf("Nulo", "Leve", "Moderado", "Alto", "Severo")

    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .requiredWidth(378.dp)
            .requiredHeight(100.dp)
            .clip(RoundedCornerShape(15.dp))
    ) {
        Text(
            text = title,
            color = colors[3],
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 15.dp, top = 7.dp)
        )
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = iconContentDescription,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .requiredSize(45.dp)
                .padding(end = 8.dp, bottom = 12.dp)
                .clickable {

                }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .border(
                    BorderStroke(2.dp, colors[3]),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(start = 15.dp, bottom = 4.dp, top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            colors.forEachIndexed { index, color ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .requiredWidth(48.dp)
                            .requiredHeight(33.dp)
                            .background(color)
                            .border(
                                width = if (index == selectedBox) 3.dp else 0.dp,
                                color = if (index == selectedBox) Color.Yellow else Color.Transparent,
                            )
                            .clickable {
                                selectedBox = index
                                // Lógica para guardar la selección
                            }
                    )
                    Text(
                        text = labels[index],
                        color = colors[3],
                        style = TextStyle(fontSize = 15.sp)
                    )
                }
            }
        }
    }
    //    Text("El ánimo seleccionado es ${if (selectedBox != -1) labels[selectedBox] else "ninguno"}")
}

@Composable
private fun Robotin() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp
            )
            .background(
                Color.LightGray,
                RoundedCornerShape(48.dp)
            )
    ) {
        Text(
            "Robotin",
            modifier = Modifier.padding(top = 6.dp, start = 25.dp),
            color = secondaryColor, fontSize = 20.sp
        )
        Text(
            "  ¡Hola! ¿Cómo\n amaneciste hoy?",
            modifier = Modifier.padding(top = 22.dp, start = 20.dp), fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painterResource(id = R.drawable.robotin),
            contentDescription = "Fondo",
            modifier = Modifier
                .size(85.dp)
                .padding(end = 1.dp)
        )
    }
}