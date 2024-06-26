package com.losrobotines.nuralign.feature_routine.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_sleep.presentation.screens.CustomTimePickerDialog
import com.losrobotines.nuralign.ui.theme.secondaryColor
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditActivityAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    activity: Activity,
    routineViewModel: RoutineViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.padding(horizontal = 15.dp),
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Editar Actividad",
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            EditActivityRow(
                activity = activity,
                onActivityNameChange = { newName ->
                    activity.name = newName
                },
                routineViewModel = routineViewModel
            )
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        routineViewModel.updateActivityName(activity, activity.name)
                        routineViewModel.updateActivityTime(activity, activity.time)
                        confirmButton()
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryColor,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                )
            ) {
                Text("Guardar cambios")
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditActivityRow(
    activity: Activity,
    onActivityNameChange: (String) -> Unit,
    routineViewModel: RoutineViewModel
) {
    val activityName = remember { mutableStateOf(activity.name) }
    val activityTime = remember { mutableStateOf(activity.time) }
    val isOpen = remember { mutableStateOf(false) }

    Column {
        Row {
            EditActivityElement(
                value = activityName.value,
                onValueChange = {
                    activityName.value = it
                    onActivityNameChange(it)
                },
                label = "Actividad"
            )
            Spacer(modifier = Modifier.width(15.dp))
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .weight(0.7f)
                    .padding(horizontal = 4.dp)
            ) {
                OutlinedTextField(
                    value = activityTime.value,
                    label = { Text("Hora") },
                    onValueChange = {
                        activityTime.value = it
                    },
                    modifier = Modifier
                        .height(65.dp)
                        .width(100.dp)
                        .clickable() { isOpen.value = true },
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
        }
        if (isOpen.value) {
            CustomTimePickerDialog(
                onAccept = { selectedTime ->
                    isOpen.value = false
                    if (selectedTime != null) {
                        val formattedTime =
                            selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                        activityTime.value = formattedTime
                        activity.time = formattedTime
                    }
                },
                onCancel = {
                    isOpen.value = false
                }
            )
        }
        SelectDaysForActivity(
            activity = activity,
            routineViewModel = routineViewModel
        )
        Spacer(modifier = Modifier.height(8.dp))
        RemoveActivity(activity, routineViewModel)
        Spacer(modifier = Modifier.height(4.dp))
        Divider(color = secondaryColor, thickness = 2.dp)
    }
}

@Composable
fun EditActivityElement(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .height(65.dp)
                .width(160.dp),
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
            label = { Text(label, color = secondaryColor) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = secondaryColor
            )
        )
    }
}

@Composable
fun RemoveActivity(activity: Activity, routineViewModel: RoutineViewModel) {
    val showDialog = remember { mutableStateOf(false) }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        ClickableText(
            AnnotatedString("Eliminar actividad."),
            onClick = { showDialog.value = true },
            modifier = Modifier
                .fillMaxWidth(),
            style = TextStyle(
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                color = Color.Red,
                fontSize = 24.sp
            )
        )

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Confirmar Eliminación",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(end=3.dp)
                        )
                    }
                },
                text = {
                    Text(
                        "¿Estás seguro de eliminar esta actividad?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            routineViewModel.removeActivity(activity)
                            showDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog.value = false },
                        colors = ButtonDefaults.buttonColors(
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
    }
}
