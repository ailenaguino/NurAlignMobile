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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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

    Column {
        LazyColumn {
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
                Text(
                    text = "Actividades",
                    fontSize = 18.sp,
                    color = secondaryColor,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    OutlinedTextField(
                        value =newActivityName,
                        onValueChange = {newActivityName=it },
                        modifier = Modifier
                            .height(65.dp)
                            .width(160.dp),
                        shape = RoundedCornerShape(20.dp),
                        enabled = !isSaved,
                        label = { Text("Actividad", color = secondaryColor) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = secondaryColor,
                            unfocusedBorderColor = secondaryColor,
                            disabledBorderColor = secondaryColor,
                            disabledTextColor = secondaryColor
                        )
                    )
                    Spacer(modifier = Modifier.width(25.dp))

                    OutlinedTextField(
                        value = newActivityTime,
                        label = { Text("Hora") },
                        onValueChange = { newActivityTime=it },
                        enabled = !isSaved,
                        modifier = Modifier
                            .height(65.dp)
                            .width(95.dp).clickable() { isOpen.value = true }
                        ,shape = RoundedCornerShape(35.dp),
                        singleLine = true,

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
                    if (isOpen.value) {
                        CustomTimePickerDialog(
                            onAccept = { selectedTime ->
                                isOpen.value = false
                                if (selectedTime != null) {
                                    newActivityTime = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                }
                            },
                            onCancel = {
                                isOpen.value = false
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(25.dp))
                    Button(
                        onClick = {
                            if (newActivityName.isNotEmpty() && newActivityTime.isNotEmpty()) {
                                routineViewModel.addActivity(
                                    Activity(
                                        name = newActivityName,
                                        time = newActivityTime,
                                        days = emptyList()
                                    )
                                )
                                newActivityName = ""
                                newActivityTime = ""
                            } else {
                                Toast.makeText(
                                    context,
                                    "Por favor, complete el nombre y la hora de la actividad",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }, enabled = !isSaved,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = mainColor,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                    ) {
                        Text("Agregar")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(activities) { activity ->
                GenericActivityRow(activity, routineViewModel)
                Spacer(modifier = Modifier.height(20.dp))
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
                    name
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ActivityRow(activity: Activity, routineViewModel: RoutineViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = activity.name,
            fontSize = 16.sp,
            color = secondaryColor,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = activity.time,
            fontSize = 16.sp,
            color = secondaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = {
            routineViewModel.removeActivity(activity)
        }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
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
    name: String
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
                    val activityMessage =
                        NotificationPrompts.getActivityMessage(activity.name, name)
                    routineViewModel.generateNotificationMessage(activityMessage)?.let {
                        routineViewModel.notification.scheduledNotification(
                            context,
                            selectedActivityTime,
                            title = "Robotin",
                            content = it,
                            destination = "HomeScreen",
                            notificationId = index + 2,
                            selectedDays = activity.days
                        )
                    }
                }

                routineViewModel.saveRoutine()
                routineViewModel.setIsSavedRoutine(true)
            }
        },
        modifier = Modifier.padding(end = 16.dp, start = 260.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = secondaryColor,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ),
        enabled = !isSaved
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