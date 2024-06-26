package com.losrobotines.nuralign.feature_medication.presentation.screens.tracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.presentation.screens.medication.AddMedicationAlertDialog
import com.losrobotines.nuralign.feature_medication.presentation.screens.medication.EditMedicationAlertDialog
import com.losrobotines.nuralign.feature_medication.presentation.screens.medication.MedicationViewModel
import com.losrobotines.nuralign.feature_mood_tracker.presentation.utils.getDayOfWeek
import com.losrobotines.nuralign.feature_mood_tracker.presentation.utils.getMonth
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MedicationTrackerScreenComponent(
    navController: NavController,
    medicationViewModel: MedicationViewModel,
    medicationTrackerViewModel: MedicationTrackerViewModel
) {
    val medicationList by medicationViewModel.medicationList.observeAsState()
    val medicationIdList = medicationList?.mapNotNull { it?.patientMedicationId }
    val isLoading by medicationViewModel.isLoading.observeAsState(initial = false)
    val route by medicationTrackerViewModel.route.observeAsState("")
    val isVisible by medicationTrackerViewModel.isVisible.observeAsState(false)
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(medicationIdList) {
        medicationIdList?.let {
            medicationTrackerViewModel.loadMedicationTrackerInfo(it)
        }
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
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        CurrentDay()
                        Spacer(modifier = Modifier.height(10.dp))
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
                            AddIcon(medicationViewModel, medicationTrackerViewModel, emptyList())
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
                            MedicationElement(
                                medicationList!![it]!!,
                                medicationViewModel,
                                medicationTrackerViewModel
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            AddIcon(
                                medicationViewModel,
                                medicationTrackerViewModel,
                                medicationIdList!!
                            )
                        }
                    }
                }
            }
            SaveButton(
                medicationTrackerViewModel,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
        SharedComponents().CompanionCongratulation(isVisible = isVisible) {
            goToNextTracker(
                navController,
                route, medicationTrackerViewModel
            )
        }
        SnackbarError(medicationViewModel, medicationTrackerViewModel, snackbarHostState)
    }
}


@Composable
fun MedicationElement(
    medicationElement: MedicationInfo,
    medicationViewModel: MedicationViewModel,
    medicationTrackerViewModel: MedicationTrackerViewModel
) {
    val medicationTrackerList by medicationTrackerViewModel.medicationTrackerList.observeAsState(
        emptyList()
    )
    val tracker =
        medicationTrackerList.find { it?.patientMedicationId == medicationElement.patientMedicationId }

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
            Checkbox(
                checked = tracker?.takenFlag == "Y",
                onCheckedChange = { newCheckedState ->
                    tracker?.let {
                        medicationTrackerViewModel.updateTakenFlag(
                            it.patientMedicationId,
                            if (newCheckedState) "Y" else "N"
                        )
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = secondaryColor,
                    uncheckedColor = secondaryColor
                )
            )
        }
    }
    medicationTrackerViewModel.setPatientMedicationId(medicationElement.patientMedicationId!!.toInt())
}

@Composable
fun AddIcon(
    medicationViewModel: MedicationViewModel,
    medicationTrackerViewModel: MedicationTrackerViewModel,
    medicationIdList: List<Short>
) {
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
                medicationViewModel = medicationViewModel,
                medicationTrackerViewModel = medicationTrackerViewModel,
                medicationIdList
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
                medicationViewModel = medicationViewModel,
                onDelete = { openAlertDialog = false }
            )
        }
    }
}

@Composable
fun SaveButton(
    medicationTrackerViewModel: MedicationTrackerViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Button(
        onClick = {
            medicationTrackerViewModel.saveOrUpdateMedicationTracker(context)
            medicationTrackerViewModel.checkNextTracker()
        },
        colors = ButtonDefaults.buttonColors(containerColor = mainColor),
        modifier = modifier
    ) {
        Text(text = "Guardar")
    }
}

@Composable
fun CurrentDay() {
    val calendar = Calendar.getInstance()
    val lineaModifier = Modifier
        .fillMaxWidth()
        .height(4.dp)

    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "${getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK))}," +
                    " ${calendar.get(Calendar.DAY_OF_MONTH)} de" +
                    " ${getMonth(calendar.get(Calendar.MONTH))}",
            color = Color.Black,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 30.dp, end = 8.dp)
        )
        Box(
            modifier = lineaModifier
                .background(mainColor)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun goToNextTracker(
    navController: NavController,
    route: String,
    medicationTrackerViewModel: MedicationTrackerViewModel,
) {
    medicationTrackerViewModel.setIsVisible(false)
    navController.navigate(route)
}

@Composable
fun SnackbarError(
    medicationViewModel: MedicationViewModel,
    medicationTrackerViewModel: MedicationTrackerViewModel,
    snackbarHostState: SnackbarHostState
) {
    val errorMessage by medicationViewModel.errorMessage.observeAsState()
    val errorMessageTracker by medicationTrackerViewModel.errorMessage.observeAsState()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            medicationViewModel.clearErrorMessage()
        }
    }
    LaunchedEffect(errorMessageTracker) {
        errorMessageTracker?.let {
            snackbarHostState.showSnackbar(it)
            medicationTrackerViewModel.clearErrorMessage()
        }
    }
}