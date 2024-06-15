package com.losrobotines.nuralign.feature_routine.presentation

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.USER_NAME
import com.losrobotines.nuralign.feature_routine.domain.NotificationPrompts
import com.losrobotines.nuralign.feature_sleep.presentation.screens.CustomTimePickerDialog
import com.losrobotines.nuralign.ui.preferences.PreferencesManager
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.secondaryColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoutineScreenComponent(navController: NavHostController, routineViewModel: RoutineViewModel) {
    val time: String by routineViewModel.bedTimeRoutine.observeAsState("")
    val isSaved by routineViewModel.isSaved.observeAsState(false)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isSecondActivityEnabled by remember { mutableStateOf(false) }
    var isActivityEnabled by remember { mutableStateOf(false) }

    val preferencesManager = remember { PreferencesManager(context) }
    val name = preferencesManager.getString(USER_NAME, "Usuario")

    Column() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
        ) {
            item {
                SharedComponents().HalfCircleTop(title = "Mi rutina")
            }
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }

            item {
                questionGoToSleep(isSaved, time, routineViewModel)
            }
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }
            item {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = "Agregar actividad",
                            fontSize = 16.sp,
                            color = secondaryColor,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = isActivityEnabled,
                            onCheckedChange = { isActivityEnabled = it },
                        )
                    }
                    if (isActivityEnabled) {
                        ActivityRow(routineViewModel)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = "Agregar segunda actividad",
                            fontSize = 16.sp,
                            color = secondaryColor,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = isSecondActivityEnabled,
                            onCheckedChange = { isSecondActivityEnabled = it }
                        )
                    }
                    if (isSecondActivityEnabled) {
                        SecondActivityRow(routineViewModel)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(70.dp))
            }
            item {
                saveRoutine(
                    routineViewModel,
                    context,
                    scope,
                    isSaved,
                    isActivityEnabled,
                    isSecondActivityEnabled,
                    name
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
    isSaved: Boolean,
    isActivityEnabled: Boolean,
    isSecondActivityEnabled: Boolean,
    name: String
) {
    Button(
        onClick = {
            val bedTime = routineViewModel.bedTimeRoutine.value
            val activityTime = routineViewModel.activityRoutineTime.value
            val activityTime2 = routineViewModel.activityRoutineTime2.value
            val selectedDays = routineViewModel.selectedDays
            val selectedDays2 = routineViewModel.selectedDays2

            if (bedTime.isNullOrEmpty()) {
                Toast.makeText(
                    context,
                    "Por favor, seleccione una hora para dormir",
                    Toast.LENGTH_SHORT
                ).show()
                return@Button
            }

            val selectedBedTime = routineViewModel.parseTime(bedTime)
            if (selectedBedTime == null) {
                Toast.makeText(
                    context,
                    "La hora de dormir es inválida",
                    Toast.LENGTH_SHORT
                ).show()
                return@Button
            }

            val prompt = NotificationPrompts.getMotivationalMessage(name)

            scope.launch {
                routineViewModel.generateNotificationMessage(prompt)?.let {
                    routineViewModel.notification.scheduledNotification(
                        context,
                        selectedBedTime,
                        title = "Robotin",
                        content = it,
                        destination = "SleepTrackerScreen",
                        notificationId = 1
                    )
                }

                if (isActivityEnabled && !activityTime.isNullOrEmpty()) {
                    val selectedActivityTime = routineViewModel.parseTime(activityTime)
                    if (selectedActivityTime == null) {
                        Toast.makeText(
                            context,
                            "La hora de la actividad es inválida",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }

                    val activityMessage =
                        NotificationPrompts.getActivityMessage(
                            routineViewModel.activity.value ?: "", name
                        )
                    routineViewModel.generateNotificationMessage(activityMessage)?.let {
                        routineViewModel.notification.scheduledNotification(
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

                if (isSecondActivityEnabled && !activityTime2.isNullOrEmpty()) {
                    val selectedActivityTime2 = routineViewModel.parseTime(activityTime2)
                    if (selectedActivityTime2 == null) {
                        Toast.makeText(
                            context,
                            "La hora de la segunda actividad es inválida",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }

                    val activity2Message =
                        NotificationPrompts.getActivityMessage(
                            routineViewModel.activity2.value ?: "", name
                        )
                    routineViewModel.generateNotificationMessage(activity2Message)?.let {
                        routineViewModel.notification.scheduledNotification(
                            context,
                            selectedActivityTime2,
                            title = "Robotin",
                            content = it,
                            destination = "HomeScreen",
                            notificationId = 3,
                            selectedDays = selectedDays2
                        )
                    }
                }

                routineViewModel.saveRoutine()
                routineViewModel.setIsSavedRoutine(true)
            }
        }, modifier = Modifier.padding(end = 16.dp,start=260.dp), colors = ButtonDefaults.buttonColors(
            containerColor = secondaryColor,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ), enabled = !isSaved
    ) {
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
        if (isSaved) {
            IconButton(onClick = {
                routineViewModel.setIsSavedRoutine(false)
            }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
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