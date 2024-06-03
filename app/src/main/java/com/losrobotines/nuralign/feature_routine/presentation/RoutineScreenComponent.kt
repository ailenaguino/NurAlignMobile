package com.losrobotines.nuralign.feature_routine.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.losrobotines.nuralign.feature_sleep.presentation.screens.CustomTimePickerDialog
import com.losrobotines.nuralign.feature_sleep.presentation.screens.QuestionGoToSleep
import com.losrobotines.nuralign.notification.Notification
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.secondaryColor
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoutineScreenComponent(navController: NavHostController, routineViewModel: RoutineViewModel) {
    val time: String by routineViewModel.bedTimeRoutine.observeAsState("")
    val isSaved by routineViewModel.isSaved.observeAsState(false)

    val context = LocalContext.current

    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
        ) {
            item {
                SharedComponents().HalfCircleTop(title = "Mi rutina")
            }
            item {
                questionGoToSleep(isSaved, time, routineViewModel, context)
            }
            item {
                Spacer(modifier = Modifier.height(90.dp))
                Button(onClick = {
                    val timeParts = time.split(":")
                    if (timeParts.size == 2) {
                        val hour = timeParts[0].toInt()
                        val minute = timeParts[1].toInt()
                        val selectedTime = LocalTime.of(hour, minute)

                        // Programar la notificación
                        routineViewModel.notification.notificacionProgramada(context, selectedTime)
                    }
                }) {
                    Text("Guardar")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun questionGoToSleep(
    isSaved: Boolean,
    time: String,
    routineViewModel: RoutineViewModel,
    context: Context
) {
    val isOpen = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 10.dp, end = 5.dp)
    ) {
        Text(
            text = "¿A qué hora sueles despertarte?",
            fontSize = 16.sp,
            color = secondaryColor,
            modifier = Modifier.weight(0.6f)
        )

        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 2.dp)
            .weight(0.4f)
            .clickable(enabled = !isSaved) { isOpen.value = true }
        ) {
            OutlinedTextField(
                value = time,
                label = { Text("Hora") },
                onValueChange = { routineViewModel.setSleepTimeRoutine(time) },
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
                    val formattedTime =
                        selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                    routineViewModel.setSleepTimeRoutine(formattedTime)

                    // Programar la notificación
                    routineViewModel.notification.notificacionProgramada(context, selectedTime)
                }
            },
            onCancel = {
                isOpen.value = false
            }
        )
    }
}