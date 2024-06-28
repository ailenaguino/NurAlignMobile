package com.losrobotines.nuralign.feature_routine.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_sleep.presentation.screens.CustomTimePickerDialog
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddActivityAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    routineViewModel: RoutineViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var newActivityName by remember { mutableStateOf("") }
    var newActivityTime by remember { mutableStateOf("") }
    var selectedDays by remember { mutableStateOf(emptyList<String>()) }
    val isSaved by routineViewModel.isSaved.observeAsState(false)
    val isOpen = remember { mutableStateOf(false) }

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.padding(horizontal = 15.dp),
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nueva actividad",
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Column {
                Row(
                    modifier = Modifier.height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        OutlinedTextField(
                            value = newActivityName,
                            onValueChange = { newActivityName = it },
                            modifier = Modifier
                                .height(155.dp)
                                .fillMaxWidth(0.9f),
                            shape = RoundedCornerShape(20.dp),
                            enabled = true,
                            singleLine = true,
                            label = { Text("Actividad", color = secondaryColor) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = secondaryColor,
                                unfocusedBorderColor = secondaryColor,
                                disabledBorderColor = secondaryColor,
                                disabledTextColor = secondaryColor
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .weight(0.7f)
                            .padding(horizontal = 4.dp)
                    ) {
                        OutlinedTextField(
                            value = newActivityTime,
                            label = { Text("Hora") },
                            onValueChange = { newActivityTime = it },
                            modifier = Modifier
                                .height(65.dp)
                                .width(90.dp)
                                .clickable(enabled = true) { isOpen.value = true },
                            shape = RoundedCornerShape(30.dp),
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
                    if (isOpen.value) {
                        CustomTimePickerDialog(
                            onAccept = { selectedTime ->
                                isOpen.value = false
                                if (selectedTime != null) {
                                    val formattedTime =
                                        selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                    newActivityTime = formattedTime
                                }
                            },
                            onCancel = {
                                isOpen.value = false
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Seleccionar días")
                Spacer(modifier = Modifier.height(2.dp))
                SelectDaysForAddActivity(
                    selectedDays = selectedDays,
                    onDaySelected = { day ->
                        selectedDays = if (selectedDays.contains(day)) {
                            selectedDays - day
                        } else {
                            selectedDays + day
                        }
                    }
                )
            }
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(onClick = {
                coroutineScope.launch {
                    routineViewModel.addActivity(
                        Activity(
                            name = newActivityName,
                            time = newActivityTime,
                            days = selectedDays
                        )
                    )
                }
                confirmButton()
            }, colors = ButtonDefaults.buttonColors(
                containerColor = secondaryColor,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            )) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismissRequest() }, colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryColor,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                )
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun SelectDaysForAddActivity(
    selectedDays: List<String>,
    onDaySelected: (String) -> Unit
) {
    val days = arrayOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do")
    Column {
        Spacer(modifier = Modifier.height(6.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            for (day in days) {
                Text(
                    text = day,
                    color = secondaryColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1.4f)
                )
            }
        }
        Row(horizontalArrangement = Arrangement.Center) {
            for (day in days) {
                val isSelected = selectedDays.contains(day)
                Button(
                    onClick = { onDaySelected(day) },
                    shape = CutCornerShape(10),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) secondaryColor else mainColor,
                        disabledContainerColor = if (isSelected) secondaryColor else Color.Gray
                    ),
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

        Spacer(modifier = Modifier.height(8.dp))

        Divider(color = secondaryColor, thickness = 2.dp)
    }
}