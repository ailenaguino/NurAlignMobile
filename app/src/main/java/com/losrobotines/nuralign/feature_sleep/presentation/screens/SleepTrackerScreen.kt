package com.losrobotines.nuralign.feature_sleep.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import kotlin.math.roundToInt
import com.losrobotines.nuralign.ui.shared.SharedComponents

@Composable
fun SleepScreen(navController: NavController,) {
    SharedComponents().HalfCircleTop("Seguimiento del sueño")
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp, 0.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(110.dp))
            SliderHour()
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            QuestionGoToSleep()
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            QuestionGeneral(q = "¿Tuviste pensamientos negativos?")
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            QuestionGeneral(q = "¿Estuviste ansioso antes de dormir?")
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            QuestionGeneral(q = "¿Dormiste de corrido?")
            Spacer(modifier = Modifier.height(8.dp))

        }

        item {
            AdditionalNotes()
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxWidth()) {
                SaveButton()
            }
        }

    }
}

@Preview
@Composable
fun SliderHour() {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Column {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.height(60.dp)
        ) {
            Text(text = "Horas de sueño", fontSize = 16.sp, color = secondaryColor)
        }

        Box(
            modifier = Modifier
                .border(
                    BorderStroke(2.dp, secondaryColor),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.weight(3.3f)) {
                    Text(text = "0", color = secondaryColor)
                }
                Box(
                    modifier = Modifier.weight(3.3f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = sliderPosition.toInt().toString(),
                        fontSize = 24.sp,
                        color = secondaryColor
                    )
                }
                Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.weight(3.3f)) {
                    Text(text = "24", color = secondaryColor)
                }

            }
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it.roundToInt().toFloat() },
                steps = 24,
                valueRange = 0f..24f,
                colors = SliderDefaults.colors(
                    thumbColor = mainColor,
                    activeTrackColor = mainColor,
                    activeTickColor = secondaryColor
                ),
                modifier = Modifier.padding(12.dp)
            )
        }

    }
}

@Preview
@Composable
fun QuestionGoToSleep() {
    var hour by remember { mutableStateOf("") }
    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {

        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.weight(0.7f)) {
            Text(text = "¿A qué hora te fuiste a dormir?", fontSize = 16.sp, color = secondaryColor)
        }

        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.weight(0.3f)) {
            OutlinedTextField(
                value = hour,
                onValueChange = { hour = it },
                modifier = Modifier
                    .height(50.dp)
                    .width(75.dp),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                ),
                placeholder = { Text(text = "00:00") }
            )
        }
    }
}

@Composable
fun QuestionGeneral(q: String) {

    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.weight(0.7f)) {
            Text(text = q, fontSize = 16.sp, color = secondaryColor)
        }

        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.weight(0.3f)) {
            var checked by remember { mutableStateOf(false) }
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                thumbContent = if (checked) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "si",
                            tint = secondaryColor,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                } else {
                    {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "no",
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = mainColor
                )
            )
        }
    }
}

@Preview
@Composable
fun AdditionalNotes() {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Notas adicionales", color = secondaryColor, textAlign = TextAlign.Center) },
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = secondaryColor,
            unfocusedBorderColor = secondaryColor
        )
    )
}

@Preview
@Composable
fun SaveButton() {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(containerColor = mainColor)
    ) {
        Text(text = "Guardar")
    }
}