package com.losrobotines.nuralign.feature_therapy.presentation.screens.therapists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

@Composable
fun TherapistScreenComponent(
    navController: NavController,
    therapistViewModel: TherapistViewModel
) {
    val therapistList by therapistViewModel.therapistList.observeAsState()
    val isLoading by therapistViewModel.isLoading.observeAsState(initial = false)
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
                        SharedComponents().HalfCircleTop(title = "Mis terapeutas")
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
                        MyTherapistsTitle()
                    }
                }
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (therapistList.isNullOrEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        item {
                            AddIcon(therapistViewModel)
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "No tienes terapeutas vinculados.", modifier = Modifier
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
                        items(therapistList!!.size) {
                            TherapistElement(
                                therapistList!![it]!!,
                                therapistViewModel
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        item {
                            AddIcon(therapistViewModel)
                        }
                    }
                }
            }
        }
        SnackbarError(therapistViewModel, snackbarHostState)
    }
}

@Composable
private fun MyTherapistsTitle() {
    Column(modifier = Modifier.padding(25.dp)) {
        Text(text = "Mis terapeutas", fontSize = 24.sp, color = secondaryColor)
        Divider(color = secondaryColor, thickness = 2.dp)
    }
}

@Composable
private fun TherapistElement(
    therapistInfo: TherapistInfo,
    therapistViewModel: TherapistViewModel
) {
    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 4.dp)
        ) {
            Text(
                text = "${therapistInfo.name} ${therapistInfo.lastName}",
                modifier = Modifier
                    .fillMaxWidth(),
                color = secondaryColor,
                fontSize = 20.sp,
            )
        }
        Box(
            modifier = Modifier
                .wrapContentWidth(Alignment.End)
                .weight(0.2f)
        ) {
            EditTherapist(therapistInfo, therapistViewModel)
        }
        Box(
            modifier = Modifier
                .wrapContentWidth(Alignment.End)
                .weight(0.2f)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "ir al historial",
                tint = mainColor,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { /*TODO*/ }
            )
        }
    }
}

@Composable
private fun AddIcon(therapistViewModel: TherapistViewModel) {
    var openAlertDialog by remember { mutableStateOf(false) }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { openAlertDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = mainColor)
        ) {
            Text(text = "Vincular terapeuta")
        }
        if (openAlertDialog) {
            AddNewTherapistAlertDialog(
                onDismissRequest = { openAlertDialog = false },
                confirmButton = { openAlertDialog = false },
                therapistViewModel = therapistViewModel
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun EditTherapist(therapistInfo: TherapistInfo, therapistViewModel: TherapistViewModel) {
    var openAlertDialog by remember { mutableStateOf(false) }

    IconButton(
        onClick = { openAlertDialog = true },
    ) {
        Icon(Icons.Default.ModeEdit, contentDescription = "editar", tint = secondaryColor)
        if (openAlertDialog) {
            EditTherapistAlertDialog(
                onDismissRequest = { openAlertDialog = false },
                confirmButton = { openAlertDialog = false },
                therapistInfo = therapistInfo,
                therapistViewModel = therapistViewModel
            )
        }
    }
}

@Composable
fun SnackbarError(therapistViewModel: TherapistViewModel, snackbarHostState: SnackbarHostState) {
    val errorMessage by therapistViewModel.errorMessage.observeAsState()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            therapistViewModel.clearErrorMessage()
        }
    }
}
