package com.losrobotines.nuralign.feature_routine.presentation

import android.os.Build
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.losrobotines.nuralign.feature_medication.presentation.screens.medication_tracker.MedicationViewModel
import com.losrobotines.nuralign.feature_sleep.presentation.screens.CustomTimePickerDialog
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddActivityAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    routineViewModel: RoutineViewModel
) {

    val context = LocalContext.current

    AlertDialog(properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.padding(horizontal = 15.dp),
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nueva Actividad",
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            ActivityRow(routineViewModel)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(onClick = {
                confirmButton()
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("Cancelar")
            }
        }
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityElement(routineViewModel: RoutineViewModel) {
    val activity by routineViewModel.activity.observeAsState("")
    val time: String by routineViewModel.activityRoutineTime.observeAsState("")
    val isSaved by routineViewModel.isSaved.observeAsState(false)

    val isOpen = remember { mutableStateOf(false) }

    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = activity,
                onValueChange = { routineViewModel.setActivity(it) },
                modifier = Modifier
                    .height(80.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(20.dp),
                enabled = !isSaved,
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

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 40.dp, end = 2.dp)
                .size(90.dp)
                .clickable(enabled = !isSaved) { isOpen.value = true }
        ) {
            OutlinedTextField(
                value = time,
                label = { Text("Hora") },
                onValueChange = { routineViewModel.setActivityRoutine(it) },
                modifier = Modifier
                    .size(90.dp)
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
                    routineViewModel.setActivityRoutine(formattedTime)
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
fun ActivityRow(routineViewModel: RoutineViewModel) {
    // Obtener los días seleccionados del ViewModel
    val selectedDays = routineViewModel.selectedDays

    // Array de días
    val days = arrayOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do")

    Column {
        ActivityElement(routineViewModel) // Agregado aquí
        Spacer(modifier = Modifier.height(6.dp))

        // Mostrar los nombres de los días
        Row(horizontalArrangement = Arrangement.Center) {
            for (day in days) {
                Text(
                    text = day,
                    color = secondaryColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1.4f)
                )
            }
        }

        // Mostrar los botones de los días y guardar/eliminar los seleccionados en el ViewModel
        Row(horizontalArrangement = Arrangement.Center) {
            for (day in days) {
                val isSelected = selectedDays.contains(day)
                Button(
                    onClick = {
                        if (isSelected) {
                            routineViewModel.removeSelectedDay(day)
                        } else {
                            routineViewModel.addSelectedDay(day)
                        }
                    },
                    shape = CutCornerShape(10),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) secondaryColor else mainColor),
                    modifier = Modifier
                        .weight(1.4f)
                        .height(24.dp)
                        .padding(horizontal = 1.dp)
                        .defaultMinSize(12.dp)
                ) {
                    Text(text = " ") // Este texto se puede cambiar si deseas mostrar algo diferente en los botones
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Divider(color = secondaryColor, thickness = 2.dp)
    }
}