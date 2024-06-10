package com.losrobotines.nuralign.feature_medication.presentation.screens.add_medication

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.presentation.screens.medication_tracker.MedicationViewModel
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import kotlinx.coroutines.launch

@Composable
fun EditMedicationAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    medicationElement: MedicationInfo,
    medicationViewModel: MedicationViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.padding(horizontal = 15.dp),
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${medicationElement.medicationName} - ${medicationElement.medicationGrammage}",
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            EditMedicationRow(medicationViewModel, medicationElement)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(onClick = {
                coroutineScope.launch {
                    medicationViewModel.editExistingMedication(medicationElement)
                }
                confirmButton()
            }) {
                Text("Guardar cambios")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismissRequest()
                medicationViewModel.clearMedicationState()
            }) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun EditMedicationRow(medicationViewModel: MedicationViewModel, medicationElement: MedicationInfo) {
    Column {
        EditMedicationElement(medicationViewModel, medicationElement)
        Spacer(modifier = Modifier.height(8.dp))

        SharedComponents().SelectDayButtons()
        Spacer(modifier = Modifier.height(8.dp))

        EditOptional(medicationViewModel, medicationElement)
        Divider(color = secondaryColor, thickness = 2.dp)
    }
}

@Composable
fun EditMedicationElement(
    medicationViewModel: MedicationViewModel,
    medicationElement: MedicationInfo
) {
    val medicationName = remember { mutableStateOf(medicationElement.medicationName) }
    val medicationGrammage = remember { mutableIntStateOf(medicationElement.medicationGrammage) }
    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = medicationName.value,
                onValueChange = {
                    medicationName.value = it
                    medicationViewModel.medicationName.value = it
                },
                modifier = Modifier
                    .height(80.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                label = { Text("Nombre", color = secondaryColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                )
            )
        }
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(0.3f)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = medicationGrammage.intValue.toString(),
                onValueChange = {
                    val newValue = it.toIntOrNull() ?: 0
                    medicationGrammage.intValue = newValue
                    medicationViewModel.medicationGrammage.intValue = newValue
                },
                modifier = Modifier
                    .height(80.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                label = { Text("Dosis", color = secondaryColor) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                )
            )
        }
    }
}

@Composable
fun EditOptional(
    medicationViewModel: MedicationViewModel,
    medicationElement: MedicationInfo
) {
    val checkedValue = remember { mutableStateOf(medicationElement.medicationOptionalFlag == "Y") }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.BottomStart, modifier = Modifier.weight(0.7f)) {
            Text(text = "Opcional", color = secondaryColor, fontSize = 16.sp)
        }

        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.weight(0.3f)) {
            Switch(
                checked = checkedValue.value,
                onCheckedChange = {
                    checkedValue.value = it
                    medicationViewModel.medicationOptionalFlag.value = if (it) "Y" else "N"
                },
                thumbContent = if (checkedValue.value) {
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

@Composable
fun RemoveMedication(onClick: () -> Unit = {}) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Icon(
            Icons.Filled.Remove,
            tint = secondaryColor,
            contentDescription = "quitar",
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp)
                .align(Alignment.TopCenter)
                .clickable { onClick() }
        )
    }
}