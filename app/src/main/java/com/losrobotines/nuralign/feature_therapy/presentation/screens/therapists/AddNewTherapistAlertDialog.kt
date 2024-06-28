package com.losrobotines.nuralign.feature_therapy.presentation.screens.therapists


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.ui.theme.secondaryColor
import kotlinx.coroutines.launch

@Composable
fun AddNewTherapistAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    therapistViewModel: TherapistViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val newTherapist = TherapistInfo(
        therapistId = null,
        therapistViewModel.patientId.value,
        therapistViewModel.therapistFirstName.value,
        therapistViewModel.therapistLastName.value,
        therapistViewModel.therapistEmail.value,
        therapistViewModel.therapistPhone.intValue,
        registeredFlag = "N"
    )

    AlertDialog(properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.padding(horizontal = 15.dp),
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nuevo terapeuta",
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            NewTherapistElement(therapistViewModel)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(onClick = {
                coroutineScope.launch {
                    therapistViewModel.saveData(newTherapist)
                }
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

@Composable
fun NewTherapistElement(therapistViewModel: TherapistViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = therapistViewModel.therapistFirstName.value,
                onValueChange = { therapistViewModel.therapistFirstName.value = it },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                label = { Text("Nombre", color = secondaryColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = therapistViewModel.therapistLastName.value,
                onValueChange = {
                    therapistViewModel.therapistLastName.value = it
                },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                label = { Text("Apellido", color = secondaryColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = therapistViewModel.therapistEmail.value,
                onValueChange = { therapistViewModel.therapistEmail.value = it },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                label = { Text("Email", color = secondaryColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = therapistViewModel.therapistPhone.intValue.toString(),
                onValueChange = {
                    therapistViewModel.therapistPhone.intValue = it.toIntOrNull() ?: 0
                },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("Teléfono", color = secondaryColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                )
            )
        }
    }
}