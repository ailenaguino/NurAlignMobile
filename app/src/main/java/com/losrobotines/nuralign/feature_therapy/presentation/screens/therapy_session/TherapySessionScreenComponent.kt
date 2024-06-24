package com.losrobotines.nuralign.feature_therapy.presentation.screens.therapy_session

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
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
    therapySessionViewModel: TherapySessionViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }

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
                                    "Clickeame para esconder mi diálogo"
                                )
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        TherapistDropDown(therapySessionViewModel)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        SessionDateAndTime(therapySessionViewModel)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        PreSessionNotes(therapySessionViewModel)
                    }
                    item {
                        PostSessionNotes(therapySessionViewModel)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        SessionFeel()
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

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TherapistDropDown(therapySessionViewModel: TherapySessionViewModel) {
    val therapistList by therapySessionViewModel.therapistList.observeAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    var selectedTherapist by remember { mutableStateOf<TherapistInfo?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
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
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
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
                                        "${it.name} ${it.lastName}",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            },
                            onClick = {
                                selectedTherapist = it
                                therapySessionViewModel.updateSelectedTherapist(it)
                                expanded = false
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
    onCancel: () -> Unit
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
    onCancel: () -> Unit
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
                    "Notes pre-sesión",
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
                    "Notes post-sesión",
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
fun SessionFeel() {
    var selectedOption by remember { mutableIntStateOf(0) }
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButton(
                    selected = (selectedOption == 1),
                    onClick = { selectedOption = 1 },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text("Muy mal", color = secondaryColor)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButton(
                    selected = (selectedOption == 2),
                    onClick = { selectedOption = 2 },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text("Mal", color = secondaryColor)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButton(
                    selected = (selectedOption == 3),
                    onClick = { selectedOption = 3 },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text("Regular", color = secondaryColor)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButton(
                    selected = (selectedOption == 4),
                    onClick = { selectedOption = 4 },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text("Bien", color = secondaryColor)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButton(
                    selected = (selectedOption == 5),
                    onClick = { selectedOption = 5 },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text("Muy bien", color = secondaryColor)
            }
        }
    }
}


@Composable
fun SaveButton(
    therapySessionViewModel: TherapySessionViewModel,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            //Guardar terapia en db
        },
        colors = ButtonDefaults.buttonColors(containerColor = mainColor),
        modifier = modifier
    ) {
        Text(text = "Guardar")
    }
}


