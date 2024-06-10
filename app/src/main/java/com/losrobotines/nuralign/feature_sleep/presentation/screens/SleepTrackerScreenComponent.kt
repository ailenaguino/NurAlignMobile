package com.losrobotines.nuralign.feature_sleep.presentation.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import kotlin.math.roundToInt
import com.losrobotines.nuralign.ui.shared.SharedComponents
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SleepTrackerScreenComponent(navController: NavController, sleepViewModel: SleepViewModel) {
    val sliderPosition: Float by sleepViewModel.sliderPosition.observeAsState(0f)
    val negativeThoughts: Boolean by sleepViewModel.negativeThoughts.observeAsState(false)
    val anxiousBeforeSleep: Boolean by sleepViewModel.anxiousBeforeSleep.observeAsState(false)
    val sleptThroughNight: Boolean by sleepViewModel.sleptThroughNight.observeAsState(false)
    val additionalNotes: String by sleepViewModel.additionalNotes.observeAsState("")
    val time: String by sleepViewModel.bedTime.observeAsState("")
    val isSaved by sleepViewModel.isSaved.observeAsState(false)
    val route by sleepViewModel.route.observeAsState("")
    val isVisible by sleepViewModel.isVisible.observeAsState(false)

    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        item {
            SharedComponents().HalfCircleTop("Seguimiento del sueño")
        }
        item {
            LargeFloatingActionButton(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                containerColor = mainColor,
                modifier = Modifier.padding(8.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 7.dp
                )
            ) {
                SharedComponents().fabCompanion(
                    listOf(
                        "¡Buen día! ¿Cómo pasaste la noche?"
                    )
                )
            }
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp, 0.dp)
            ) {
                SliderHour(sliderPosition, isSaved) { sleepViewModel.onSliderChanged(it) }
                Spacer(modifier = Modifier.height(24.dp))


                QuestionGoToSleep(sleepViewModel, time, isSaved)
                Spacer(modifier = Modifier.height(8.dp))


                QuestionGeneral(
                    q = "¿Tuviste pensamientos negativos?",
                    checked = negativeThoughts,
                    isSaved,
                    onCheckedChange = { sleepViewModel.setNegativeThoughts(it) }
                )
                Spacer(modifier = Modifier.height(8.dp))


                QuestionGeneral(
                    q = "¿Estuviste ansioso antes de dormir?",
                    checked = anxiousBeforeSleep,
                    isSaved,
                    onCheckedChange = { sleepViewModel.setAnxiousBeforeSleep(it) }
                )
                Spacer(modifier = Modifier.height(8.dp))


                QuestionGeneral(
                    q = "¿Dormiste de corrido?",
                    checked = sleptThroughNight,
                    isSaved,
                    onCheckedChange = { sleepViewModel.setSleptThroughNight(it) }
                )
                Spacer(modifier = Modifier.height(8.dp))

                AdditionalNotes(sleepViewModel, isSaved)
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SaveButton(sleepViewModel, sliderPosition, isSaved)
                }
            }
        }
    }
    SharedComponents().CompanionCongratulation(isVisible = isVisible) {
        goToNextTracker(navController, route, sleepViewModel)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun goToNextTracker(navController: NavController, route: String, sleepViewModel: SleepViewModel){
    sleepViewModel.setIsVisible(false)
    navController.navigate(route)
}

@Composable
fun QuestionGeneral(
    q: String,
    checked: Boolean,
    isSaved: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.weight(0.7f)) {
            Text(text = q, fontSize = 16.sp, color = secondaryColor)
        }

        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.weight(0.3f)) {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = true,
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
                    checkedTrackColor = mainColor,
                    disabledCheckedTrackColor = secondaryColor,
                    disabledUncheckedTrackColor = Color.Gray
                )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdditionalNotes(sleepViewModel: SleepViewModel, isSaved: Boolean) {
    val context = LocalContext.current.applicationContext
    val additionalNotes by sleepViewModel.additionalNotes.observeAsState("")

    var text by remember { mutableStateOf(additionalNotes) }

    LaunchedEffect(additionalNotes) {
        text = additionalNotes
    }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            if (newText.length <= 300) {
                text = newText
                sleepViewModel.setAdditionalNotes(text)
            } else {
                Toast.makeText(context, "Máximo 300 caracteres", Toast.LENGTH_SHORT).show()
            }
        },
        label = {
            Text(
                "Notas adicionales",
                color = secondaryColor,
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = secondaryColor,
            unfocusedBorderColor = secondaryColor,
            disabledBorderColor = secondaryColor,
            disabledTextColor = Color.Gray
        ),
        enabled = true
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SaveButton(sleepViewModel: SleepViewModel, sliderPosition: Float, isSaved: Boolean) {
    val context = LocalContext.current.applicationContext
    Button(
        onClick = {
            if (sleepViewModel.bedTime.value == "") {
                Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                sleepViewModel.setSleepTime(sliderPosition.toInt())
                sleepViewModel.saveData()
                //sleepViewModel.setIsSaved(true)
                sleepViewModel.checkNextTracker()
                sleepViewModel.setIsVisible(true)
            }

        },
        colors = ButtonDefaults.buttonColors(
            containerColor = secondaryColor,
            disabledContainerColor = secondaryColor,
            disabledContentColor = Color.White
        ),
        enabled = true
    ) {
        Text(text = "Guardar")
    }
}

@Composable
fun SliderHour(sliderPosition: Float, isSaved: Boolean, onSliderChanged: (Float) -> Unit) {

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
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.weight(3.3f)
                ) {
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
                onValueChange = { onSliderChanged(it.roundToInt().toFloat()) },
                steps = 24,
                enabled = true,
                valueRange = 0f..24f,
                colors = SliderDefaults.colors(
                    thumbColor = mainColor,
                    activeTrackColor = mainColor,
                    activeTickColor = secondaryColor,
                    disabledActiveTrackColor = secondaryColor,
                    disabledThumbColor = secondaryColor,
                ),
                modifier = Modifier.padding(12.dp)
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuestionGoToSleep(sleepViewModel: SleepViewModel, time: String, isSaved: Boolean) {
    val isOpen = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {

        Text(text = "¿A qué hora te fuiste a dormir?", fontSize = 16.sp, color = secondaryColor, modifier = Modifier.weight(0.6f))

        //Spacer(modifier = Modifier.width(16.dp))

        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 2.dp)
            .weight(0.4f)
            .clickable(enabled = true) { isOpen.value = true }
        ) {
            OutlinedTextField(
                value = time,
                label = { Text("Hora") },
                onValueChange = { sleepViewModel.setSleepTime(time) },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                shape = RoundedCornerShape(35.dp),
                singleLine = true,
                enabled = false,
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Blue,
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor,
                    disabledBorderColor = secondaryColor,
                    disabledLabelColor = secondaryColor,
                    disabledTextColor = secondaryColor
                )
            )
        }
    }
    if (isOpen.value) {
        CustomTimePickerDialog(
            onAccept = { selectedTime ->
                isOpen.value = false
                if (selectedTime != null) {
                    val formattedTime = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                    sleepViewModel.setSleepTime(formattedTime)
                }
            },
            onCancel = {
                isOpen.value = false
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    onAccept: (LocalTime?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberTimePickerState()
    val cal = Calendar.getInstance()

    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = androidx.compose.material3.MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Seleccione la hora",
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TimePicker(state = state)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onCancel) {
                        Text("Cancelar")
                    }
                    TextButton(onClick = {
                        cal.set(Calendar.HOUR_OF_DAY, state.hour)
                        cal.set(Calendar.MINUTE, state.minute)
                        val selectedTime = LocalTime.of(state.hour, state.minute)
                        onAccept(selectedTime)
                    }) {
                        Text("Aceptar")
                    }
                }
            }
        }
    }
}
