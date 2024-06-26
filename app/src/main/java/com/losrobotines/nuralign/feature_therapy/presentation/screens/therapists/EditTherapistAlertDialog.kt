package com.losrobotines.nuralign.feature_therapy.presentation.screens.therapists

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.ui.theme.secondaryColor

@Composable
fun EditTherapistAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    therapistInfo: TherapistInfo,
    therapistViewModel: TherapistViewModel
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
                    text = "${therapistInfo.name} ${therapistInfo.lastName}",
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            EditTherapistRow(therapistViewModel, therapistInfo)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(onClick = {
                if (therapistViewModel.therapistFirstName.value.isBlank() || therapistViewModel.therapistLastName.value.isBlank()) {
                    Toast.makeText(
                        context,
                        "Por favor, complete los campos de Nombre y Apellido del terapeuta",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    therapistViewModel.editExistingTherapist(therapistInfo)
                    confirmButton()
                }
            }) {
                Text("Guardar cambios")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismissRequest()
                therapistViewModel.clearTherapistState()
            }) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun EditTherapistRow(
    therapistViewModel: TherapistViewModel,
    therapistInfo: TherapistInfo
) {
    Column {
        EditTherapistElement(therapistViewModel, therapistInfo)
        Spacer(modifier = Modifier.height(8.dp))
        /*
        Divider(color = secondaryColor, thickness = 2.dp)
        Spacer(modifier = Modifier.height(8.dp))

        RemoveTherapist(therapistInfo, therapistViewModel)

        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = secondaryColor, thickness = 2.dp)
        */
    }
}

@Composable
fun EditTherapistElement(
    therapistViewModel: TherapistViewModel,
    therapistInfo: TherapistInfo
) {
    val therapistFirstName = remember { mutableStateOf(therapistInfo.name) }
    val therapistLastName = remember { mutableStateOf(therapistInfo.lastName) }
    val therapistEmail = remember { mutableStateOf(therapistInfo.email) }
    val therapistPhone = remember { mutableIntStateOf(therapistInfo.phoneNumber) }

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
                value = therapistFirstName.value,
                onValueChange = {
                    therapistFirstName.value = it
                    therapistViewModel.therapistFirstName.value = it
                },
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
                value = therapistLastName.value,
                onValueChange = {
                    therapistLastName.value = it
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
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = therapistEmail.value,
                onValueChange = {
                    therapistEmail.value = it
                    therapistViewModel.therapistEmail.value = it
                },
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
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = therapistPhone.intValue.toString(),
                onValueChange = {
                    val newValue = it.toIntOrNull() ?: 0
                    therapistPhone.intValue = newValue
                    therapistViewModel.therapistPhone.intValue = newValue
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

/*
@Composable
fun RemoveTherapist(
    therapistInfo: TherapistInfo,
    therapistViewModel: TherapistViewModel
) {
    val showDialog = remember { mutableStateOf(false) }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        ClickableText(
            AnnotatedString("Desvincular terapeuta."),
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
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Confirmar Desvinculación",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                },
                text = {
                    Text(
                        "¿Estás seguro de desvincular este terapeuta?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            therapistViewModel.removeTherapistFromList(therapistInfo)
                            showDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Desvincular")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog.value = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
 */