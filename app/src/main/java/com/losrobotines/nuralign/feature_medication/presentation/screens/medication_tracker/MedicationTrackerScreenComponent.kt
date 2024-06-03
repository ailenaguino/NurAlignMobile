package com.losrobotines.nuralign.feature_medication.presentation.screens.medication_tracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.feature_medication.presentation.screens.add_medication.AddMedicationAlertDialog
import com.losrobotines.nuralign.feature_medication.presentation.screens.add_medication.EditMedicationAlertDialog
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

@Composable
fun MedicationTrackerScreenComponent(
    navController: NavController,
    addMedicationViewModel: MedicationViewModel
) {
    val medicationList =
        remember { mutableIntStateOf(3) } //tiene que ser lista live data

    Column {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(medicationList.intValue) {
                MedicationElement()
                Spacer(modifier = Modifier.height(5.dp))
            }
            item {
                AddIcon(addMedicationViewModel)
            }
        }

    }
}

@Composable
fun MedicationElement() {
    val medicationName by remember { mutableStateOf("Desvelafaxina") }
    val medicationGrammage by remember { mutableIntStateOf(200) }

    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 4.dp)
        ) {
            Text(
                "$medicationName - $medicationGrammage",
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
            EditMedication(medicationName, medicationGrammage)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .weight(0.1f)
                .padding(horizontal = 4.dp)
        ) {
            var isChecked by remember { mutableStateOf(true) }
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
fun AddIcon(addMedicationViewModel: MedicationViewModel) {
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
                confirmButton = { openAlertDialog = false }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun EditMedication(medicationName: String, medicationGrammage: Int) {
    var openAlertDialog by remember { mutableStateOf(false) }

    IconButton(
        onClick = { openAlertDialog = true },
    ) {
        Icon(Icons.Default.ModeEdit, contentDescription = "editar", tint = secondaryColor)
        if (openAlertDialog) {
            EditMedicationAlertDialog(
                onDismissRequest = { openAlertDialog = false },
                confirmButton = { openAlertDialog = false },
                medicationName = medicationName,
                medicationGrammage = medicationGrammage
            )
        }
    }
}