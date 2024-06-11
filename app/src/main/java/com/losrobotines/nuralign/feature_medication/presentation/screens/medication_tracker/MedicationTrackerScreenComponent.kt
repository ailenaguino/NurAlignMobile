package com.losrobotines.nuralign.feature_medication.presentation.screens.medication_tracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.presentation.screens.add_edit_medication.AddMedicationAlertDialog
import com.losrobotines.nuralign.feature_medication.presentation.screens.add_edit_medication.EditMedicationAlertDialog
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

@Composable
fun MedicationTrackerScreenComponent(
    navController: NavController,
    medicationViewModel: MedicationViewModel
) {
    val medicationList by medicationViewModel.medicationList.observeAsState()
    val isLoading by medicationViewModel.isLoading.observeAsState(initial = false)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                item {
                    SharedComponents().HalfCircleTop(title = "Toma diaria de medicación")
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
                                "¡Hola! ¿Cómo estás hoy?",
                                "Clickeame para esconder mi diálogo"
                            )
                        )
                    }
                }
            }
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (medicationList.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    item {
                        AddIcon(medicationViewModel)
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No tienes medicaciones guardadas.", modifier = Modifier
                                    .fillMaxWidth(),
                                color = secondaryColor,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(medicationList!!.size) {
                        MedicationElement(medicationList!![it]!!, medicationViewModel)
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    item {
                        AddIcon(medicationViewModel)
                    }
                }
            }
        }
        SaveButton(
            medicationViewModel,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}


@Composable
fun MedicationElement(medicationElement: MedicationInfo, medicationViewModel: MedicationViewModel) {
    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 4.dp)
        ) {
            Text(
                "${medicationElement.medicationName} - ${medicationElement.medicationGrammage}",
                modifier = Modifier
                    .fillMaxWidth(),
                color = secondaryColor,
                fontSize = 24.sp
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(0.15f)
                .padding(horizontal = 4.dp)
        ) {
            EditMedication(medicationElement, medicationViewModel)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .weight(0.1f)
                .padding(horizontal = 4.dp)
        ) {
            var isChecked by remember { mutableStateOf(false) }
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = secondaryColor,
                    uncheckedColor = secondaryColor
                )
            )
        }
    }
}

@Composable
fun AddIcon(medicationViewModel: MedicationViewModel) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { openAlertDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = mainColor)
        ) {
            Text(text = "Agregar medicación")
        }
        if (openAlertDialog) {
            AddMedicationAlertDialog(
                onDismissRequest = { openAlertDialog = false },
                confirmButton = { openAlertDialog = false },
                medicationViewModel = medicationViewModel
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun EditMedication(medicationToEdit: MedicationInfo, medicationViewModel: MedicationViewModel) {
    var openAlertDialog by remember { mutableStateOf(false) }

    IconButton(
        onClick = { openAlertDialog = true },
    ) {
        Icon(Icons.Default.ModeEdit, contentDescription = "editar", tint = secondaryColor)
        if (openAlertDialog) {
            EditMedicationAlertDialog(
                onDismissRequest = { openAlertDialog = false },
                confirmButton = { openAlertDialog = false },
                medicationElement = medicationToEdit,
                medicationViewModel = medicationViewModel
            )
        }
    }
}

@Composable
fun SaveButton(medicationViewModel: MedicationViewModel, modifier: Modifier = Modifier) {
    Button(
        onClick = { // medicationViewModel.saveData()
            medicationViewModel.checkNextTracker()
        },
        colors = ButtonDefaults.buttonColors(containerColor = mainColor),
        modifier = modifier
    ) {
        Text(text = "Guardar")
    }
}