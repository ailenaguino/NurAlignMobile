package com.losrobotines.nuralign.feature_therapy.presentation.screens.therapy_session

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItemDefaults.containerColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TherapySessionScreenComponent(
    navController: NavController,
    therapySessionViewModel: TherapySessionViewModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isEditingSession by therapySessionViewModel.isEditingSession.observeAsState(false)

    BackHandler {
        if (isEditingSession) {
            therapySessionViewModel.clearSessionState()
            therapySessionViewModel.updateIsEditingSession(false)
        }
        navController.navigateUp()
        navController.navigateUp()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                    item {
                        SharedComponents().HalfCircleTop(title = "Sesión de terapia")
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
                                    "Aquí podras completar los datos de tu sesión de terapia",
                                    "Haz click en los íconos para seleccionar día y hora de la sesión",
                                    "¡Recuerda volver a entrar luego de cada sesión para completar todos los datos!",
                                    "Clickeame para esconder mi diálogo"
                                )
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        TherapistDropDown(therapySessionViewModel)
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        SessionDateAndTime(therapySessionViewModel)
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        PreSessionNotes(therapySessionViewModel)
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        PostSessionNotes(therapySessionViewModel)
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        SessionFeel(therapySessionViewModel)
                    }
                    item {
                        Box {
                            SaveButton(
                                therapySessionViewModel,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
        SnackbarError(therapySessionViewModel, snackbarHostState)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TherapistDropDown(therapySessionViewModel: TherapySessionViewModel) {
    val therapistList by therapySessionViewModel.therapistList.observeAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    val selectedTherapist by therapySessionViewModel.selectedTherapist.observeAsState(null)

    var wasSelected by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = if (selectedTherapist != null && !wasSelected) {
                    false
                } else {
                    !expanded
                }
            },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            Surface(
                color = Color.Transparent,
            ) {
                TextField(
                    value = if (selectedTherapist != null) "${selectedTherapist!!.name} ${selectedTherapist!!.lastName}" else "",
                    onValueChange = {},
                    label = { if (selectedTherapist == null) Text("Selecciona un terapeuta.") },
                    readOnly = true,
                    trailingIcon = {
                        if ((selectedTherapist != null && wasSelected) || (selectedTherapist == null)) ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    shape = RoundedCornerShape(35.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                        cursorColor = Color.Transparent,
                        focusedIndicatorColor = mainColor,
                        unfocusedIndicatorColor = mainColor,
                        disabledIndicatorColor = Color.Transparent,
                        focusedTextColor = secondaryColor,
                        unfocusedTextColor = secondaryColor,
                        disabledTextColor = secondaryColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 1.dp, end = 1.dp)
                        .menuAnchor(),
                    textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 20.sp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    therapistList.forEach {
                        DropdownMenuItem(
                            text = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        "${it!!.name} ${it.lastName}",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            },
                            onClick = {
                                therapySessionViewModel.updateSelectedTherapist(it!!)
                                expanded = false
                                wasSelected = true
                            }
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionDateAndTime(therapySessionViewModel: TherapySessionViewModel) {
    val selectedDate by therapySessionViewModel.selectedDate.observeAsState("")
    val selectedTime by therapySessionViewModel.selectedTime.observeAsState("")
    val dateIsOpen = remember { mutableStateOf(false) }
    val timeIsOpen = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.weight(0.6f)
        ) {
            TextField(
                value = selectedDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Día") },
                placeholder = {
                    Text(
                        LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    )
                },
                modifier = Modifier.weight(0.7f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    cursorColor = Color.Transparent,
                    focusedIndicatorColor = mainColor,
                    unfocusedIndicatorColor = mainColor,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = secondaryColor,
                    unfocusedTextColor = secondaryColor,
                    disabledTextColor = secondaryColor
                )
            )

            IconButton(
                onClick = { dateIsOpen.value = true },
                modifier = Modifier.weight(0.3f),
            ) {
                Icon(Icons.Filled.CalendarMonth, contentDescription = "Día", tint = secondaryColor)
            }
        }
        Row(
            modifier = Modifier.weight(0.4f),
        ) {
            TextField(
                value = selectedTime,
                onValueChange = {},
                readOnly = true,
                label = { Text("Hora") },
                placeholder = { Text("00:00") },
                modifier = Modifier.weight(0.7f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    cursorColor = Color.Transparent,
                    focusedIndicatorColor = mainColor,
                    unfocusedIndicatorColor = mainColor,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = secondaryColor,
                    unfocusedTextColor = secondaryColor,
                    disabledTextColor = secondaryColor
                )
            )

            IconButton(
                onClick = { timeIsOpen.value = true },
                modifier = Modifier.weight(0.3f),
            ) {
                Icon(Icons.Filled.AccessTime, contentDescription = "Hora", tint = secondaryColor)
            }
        }

        if (dateIsOpen.value) {
            CustomDatePickerDialog(therapySessionViewModel,
                onCancel = { dateIsOpen.value = false })
        }

        if (timeIsOpen.value) {
            CustomTimePickerDialog(therapySessionViewModel,
                onCancel = { timeIsOpen.value = false })
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    therapySessionViewModel: TherapySessionViewModel,
    onCancel: () -> Unit,
) {
    val state = rememberTimePickerState()

    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Seleccione la hora de la sesión",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TimePicker(state = state)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onCancel) {
                        Text("Cancelar")
                    }
                    TextButton(onClick = {
                        val hour = state.hour
                        val minute = state.minute

                        val formattedTime = String.format("%02d:%02d", hour, minute)
                        therapySessionViewModel.updateSelectedTime(formattedTime)

                        onCancel()
                    }) {
                        Text("Aceptar")
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    therapySessionViewModel: TherapySessionViewModel,
    onCancel: () -> Unit,
) {
    val state = rememberDatePickerState()
    val context = LocalContext.current

    DatePickerDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = {
                val dayInMillis = state.selectedDateMillis
                if (dayInMillis != null) {
                    val date =
                        dayInMillis.let {
                            Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate()
                        }
                    val formattedDate = DateTimeFormatter
                        .ofPattern("dd/MM/yyyy")
                        .format(date)
                    therapySessionViewModel.updateSelectedDate(formattedDate)
                    onCancel()
                } else {
                    Toast.makeText(
                        context,
                        "Seleccione una fecha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(
            state = state,
            title = {},
            headline = {
                if (state.selectedDateMillis != null) {
                    DatePickerDefaults.DatePickerHeadline(
                        state = state,
                        dateFormatter = DatePickerFormatter(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                } else {
                    Text(
                        "Seleccione la fecha de la sesión",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                    )
                }
            },
            showModeToggle = false
        )
    }
}

@Composable
fun PreSessionNotes(therapySessionViewModel: TherapySessionViewModel) {
    val preSessionNotes by therapySessionViewModel.preSessionNotes.observeAsState("")
    val context = LocalContext.current.applicationContext

    Box(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = preSessionNotes,
            onValueChange = { newText ->
                if (newText.length <= 300) {
                    therapySessionViewModel.updatePreSessionNotes(newText)
                } else {
                    Toast.makeText(context, "Máximo 300 caracteres", Toast.LENGTH_SHORT).show()
                }
            },
            label = {
                Text(
                    "Notas pre-sesión",
                    color = secondaryColor,
                    textAlign = TextAlign.Center
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = secondaryColor,
                disabledBorderColor = secondaryColor,
                disabledTextColor = Color.Gray
            ),
            enabled = true
        )
    }
}

@Composable
fun PostSessionNotes(therapySessionViewModel: TherapySessionViewModel) {
    val postSessionNotes by therapySessionViewModel.postSessionNotes.observeAsState("")
    val context = LocalContext.current.applicationContext

    Box(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = postSessionNotes,
            onValueChange = { newText ->
                if (newText.length <= 300) {
                    therapySessionViewModel.updatePostSessionNotes(newText)
                } else {
                    Toast.makeText(context, "Máximo 300 caracteres", Toast.LENGTH_SHORT).show()
                }
            },
            label = {
                Text(
                    "Notas post-sesión",
                    color = secondaryColor,
                    textAlign = TextAlign.Center
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = secondaryColor,
                disabledBorderColor = secondaryColor,
                disabledTextColor = Color.Gray
            ),
            enabled = true
        )
    }
}

@Composable
fun SessionFeel(therapySessionViewModel: TherapySessionViewModel) {
    val selectedOption by therapySessionViewModel.sessionFeel.observeAsState(0)
    val options = listOf(
        "Muy mal" to "1",
        "Mal" to "2",
        "Regular" to "3",
        "Bien" to "4",
        "Muy bien" to "5"
    )
    val colors = listOf(
        Color(0xff9ebadc),
        Color(0xff678bb7),
        Color(0xff385f8e),
        Color(0xff1b477c),
        Color(0xff001e41)
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text("¿Cómo te sentiste durante la sesión?", color = secondaryColor)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEachIndexed { index, (text, value) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            therapySessionViewModel.updateSessionFeel(value)
                        },
                        modifier = Modifier
                            .width(60.dp)
                            .height(40.dp)
                            .border(
                                width = if (selectedOption == value) 3.dp else 0.dp,
                                color = if (selectedOption == value) Color.Yellow else Color.Transparent
                            ),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors[index],
                            contentColor = Color.White
                        ),
                        content = {}
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text, color = secondaryColor)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun SaveButton(
    therapySessionViewModel: TherapySessionViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Button(
        onClick = {
            if (therapySessionViewModel.selectedDate.value.isNullOrEmpty() || therapySessionViewModel.selectedTime.value.isNullOrEmpty() || therapySessionViewModel.selectedTherapist.value == null) {
                Toast.makeText(
                    context,
                    "Por favor complete los campos de terapeuta, fecha y hora",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                therapySessionViewModel.saveTherapySession(context)
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = mainColor),
        modifier = modifier
    ) {
        Text(text = "Guardar")
    }
}

@Composable
fun SnackbarError(
    therapySessionViewModel: TherapySessionViewModel,
    snackbarHostState: SnackbarHostState
) {
    val errorMessage by therapySessionViewModel.errorMessage.observeAsState()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            therapySessionViewModel.clearErrorMessage()
        }
    }
}