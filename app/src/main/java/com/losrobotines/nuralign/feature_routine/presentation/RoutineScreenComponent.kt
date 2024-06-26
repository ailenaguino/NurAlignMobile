package com.losrobotines.nuralign.feature_routine.presentation

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.USER_NAME
import com.losrobotines.nuralign.feature_medication.presentation.screens.medication.AddMedicationAlertDialog
import com.losrobotines.nuralign.feature_medication.presentation.screens.medication.MedicationViewModel
import com.losrobotines.nuralign.feature_medication.presentation.screens.tracker.AddIcon
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.notification.NotificationPrompts
import com.losrobotines.nuralign.feature_sleep.presentation.screens.CustomTimePickerDialog
import com.losrobotines.nuralign.ui.preferences.PreferencesManager
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
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
    var newActivityName by remember { mutableStateOf("") }
    var newActivityTime by remember { mutableStateOf("") }
    val activities by routineViewModel.activities.observeAsState(emptyList())
    val isOpen = remember { mutableStateOf(false) }

    val preferencesManager = remember { PreferencesManager(context) }
    val name = preferencesManager.getString(USER_NAME, "Usuario")

    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1)
        ) {
            item {
                SharedComponents().HalfCircleTop(title = "Mi rutina")
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
                            "Acá completa tu rutina semanal así te recuerdo de tus actividades.",
                            "Además podes agregar que actividades haces cada día.",
                            "Clickeame para esconder mi diálogo"
                        )
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                questionGoToSleep(isSaved, time, routineViewModel)
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Text(
                    text = "Actividades",
                    fontSize = 22.sp,
                    color = secondaryColor,
                    modifier = Modifier
                        .padding(start = 15.dp),
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                AddRoutineButton(routineViewModel)
            }
            if (activities.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "No tienes actividades guardadas.", modifier = Modifier
                            .fillMaxWidth(),
                        color = secondaryColor,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }else {
                items(activities) { activity ->
                    GenericActivityRow(activity, routineViewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }

            item {
                saveRoutine(
                    routineViewModel = routineViewModel,
                    context = context,
                    scope = scope,
                    name = name
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
    name: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {
                val bedTime = routineViewModel.bedTimeRoutine.value
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
                    routineViewModel.generateNotificationMessage(prompt)
                        ?.let { motivationalMessage ->
                            routineViewModel.notification.scheduledNotification(
                                context = context,
                                selectedTime = selectedBedTime,
                                title = "Robotin",
                                content = motivationalMessage,
                                destination = "SleepTrackerScreen",
                                notificationId = 1
                            )
                        }

                    routineViewModel.activities.value?.forEachIndexed { index, activity ->
                        val selectedActivityTime = routineViewModel.parseTime(activity.time)
                        if (selectedActivityTime == null) {
                            Toast.makeText(
                                context,
                                "La hora de la actividad ${activity.name} es inválida",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@forEachIndexed
                        }

                        Log.d("RoutineViewModel", "selectedActivityTime: $selectedActivityTime")
                        Log.d("RoutineViewModel", "${activity.days}")
                        val activityMessage =
                            NotificationPrompts.getActivityMessage(activity.name, name)
                        routineViewModel.generateNotificationMessage(activityMessage)
                            ?.let { activityNotificationMessage ->
                                routineViewModel.notification.scheduledNotification(
                                    context = context,
                                    selectedTime = selectedActivityTime,
                                    title = "Robotin",
                                    content = activityNotificationMessage,
                                    destination = "HomeScreen",
                                    notificationId = index + 2,
                                    selectedDays = activity.days ?: emptyList()
                                )
                            }
                    }

                    routineViewModel.saveRoutine()
                    routineViewModel.setIsSavedRoutine(true)
                }

                Toast.makeText(context, "Rutina guardada", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.padding(end = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = secondaryColor,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            ),
            enabled = true
        )
        {
            Text("Guardar")
        }
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
                .size(100.dp)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddRoutineButton(routineViewModel: RoutineViewModel) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { openAlertDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = mainColor)
        ) {
            Text(text = "Agregar medicación")
        }
        if (openAlertDialog) {
            AddActivityAlertDialog(
                onDismissRequest = { openAlertDialog = false },
                confirmButton = { openAlertDialog = false },
                routineViewModel = routineViewModel
            )


        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
