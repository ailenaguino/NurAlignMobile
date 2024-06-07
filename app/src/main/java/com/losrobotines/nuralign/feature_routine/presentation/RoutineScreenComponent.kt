package com.losrobotines.nuralign.feature_routine.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.losrobotines.nuralign.feature_medication.presentation.screens.add_medication.AddMedicationAlertDialog
import com.losrobotines.nuralign.feature_medication.presentation.screens.medication_tracker.MedicationViewModel
import com.losrobotines.nuralign.feature_routine.domain.NotificationPrompts
import com.losrobotines.nuralign.feature_sleep.presentation.screens.CustomTimePickerDialog
import com.losrobotines.nuralign.feature_sleep.presentation.screens.QuestionGoToSleep
import com.losrobotines.nuralign.feature_routine.domain.notification.Notification
import com.losrobotines.nuralign.feature_routine.domain.notification.NotificationHelper
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoutineScreenComponent(navController: NavHostController, routineViewModel: RoutineViewModel) {
    val time: String by routineViewModel.bedTimeRoutine.observeAsState("")
    val isSaved by routineViewModel.isSaved.observeAsState(false)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
        ) {
            item {
                SharedComponents().HalfCircleTop(title = "Mi rutina")
            }
            item {
                questionGoToSleep(isSaved, time, routineViewModel)
            }
            item {
                addActivity(routineViewModel)
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                saveRoutine(routineViewModel, context, scope, isSaved)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun addActivity(routineViewModel: RoutineViewModel) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row {
            Text(
                text = "¿Qué actividad haces en tu semana?",
                fontSize = 16.sp,
                color = secondaryColor,
                modifier = Modifier
                    .weight(0.6f)
                    .padding(start = 5.dp, top = 8.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                modifier = Modifier.padding(end = 5.dp),
                onClick = { openAlertDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = secondaryColor)
            ) {
                Text(text = "Agregar")
            }
            if (openAlertDialog) {
                AddActivityAlertDialog(
                    onDismissRequest = { openAlertDialog = false },
                    confirmButton = { openAlertDialog = false },
                    routineViewModel = routineViewModel,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun saveRoutine(
    routineViewModel: RoutineViewModel,
    context: Context,
    scope: CoroutineScope,
    isSaved: Boolean
) {
    Button(onClick = {
        val bedTime = routineViewModel.bedTimeRoutine.value
        val activityTime = routineViewModel.activityRoutineTime.value
        val selectedDays = routineViewModel.selectedDays

        if (bedTime.isNullOrEmpty()) {
            Toast.makeText(
                context,
                "Por favor, seleccione una hora para dormir",
                Toast.LENGTH_SHORT
            ).show()
            return@Button
        }

        val bedTimeParts = bedTime.split(":")
        val bedTimeHour = bedTimeParts[0].toInt()
        val bedTimeMinute = bedTimeParts[1].toInt()
        val selectedBedTime = LocalTime.of(bedTimeHour, bedTimeMinute)

        val prompt=NotificationPrompts.MOTIVATIONAL_MESSAGE

        scope.launch {
            routineViewModel.generateNotificationMessage(prompt)?.let {
                routineViewModel.notification.notificacionProgramada(
                    context,
                    selectedBedTime,
                    title = "Robotin",
                    content = it,
                    destination = "SleepTrackerScreen",
                    notificationId = 1
                )
            }

            if (!activityTime.isNullOrEmpty()) {
                val activityTimeParts = activityTime.split(":")
                if (activityTimeParts.size != 2) {
                    Toast.makeText(
                        context,
                        "La hora de la actividad es inválida",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                val activityHour = activityTimeParts[0].toInt()
                val activityMinute = activityTimeParts[1].toInt()
                val selectedActivityTime = LocalTime.of(activityHour, activityMinute)


                val activityMessage = NotificationPrompts.getActivityMessage(routineViewModel.activity.value ?: "")

                routineViewModel.generateNotificationMessage(activityMessage)?.let {
                    routineViewModel.notification.notificacionProgramada(
                        context,
                        selectedActivityTime,
                        title = "Robotin",
                        content = it,
                        destination = "HomeScreen",
                        notificationId = 2,
                        selectedDays = selectedDays
                    )
                }
            }

            routineViewModel.saveRoutine()
            routineViewModel.setIsSavedRoutine(true)
        }
    }, enabled = !isSaved) {
        Text("Guardar")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun questionGoToSleep(
    isSaved: Boolean,
    time: String,
    routineViewModel: RoutineViewModel
) {
    val isOpen = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        Text(
            text = "¿A qué hora sueles despertarte?",
            fontSize = 16.sp,
            color = secondaryColor,
            modifier = Modifier.weight(0.6f)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 40.dp, end = 2.dp)
                .size(110.dp)
                .clickable(enabled = !isSaved) { isOpen.value = true }
        ) {
            OutlinedTextField(
                value = time,
                label = { Text("Hora") },
                onValueChange = { routineViewModel.setSleepTimeRoutine(it) },
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
                }
            },
            onCancel = {
                isOpen.value = false
            }
        )
    }
}