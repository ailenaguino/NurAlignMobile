package com.losrobotines.nuralign.feature_therapy.presentation.screens.session_history

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo
import com.losrobotines.nuralign.feature_therapy.presentation.screens.therapy_session.TherapySessionViewModel
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

@Composable
fun TherapySessionHistoryScreenComponent(
    navController: NavController,
    sessionHistoryViewModel: TherapySessionHistoryViewModel,
    therapySessionViewModel: TherapySessionViewModel
) {
    //val sessionHistoryList by sessionHistoryViewModel.sessionHistoryList.observeAsState()
    //val sessionTherapist by sessionHistoryViewModel.sessionTherapist.observeAsState()
    val sessionHistoryList: List<TherapySessionInfo>
    val sessionTherapist =
        TherapistInfo(2, "William", "Scottman", "wscottman@gmail.com", 1112344321, "N")
    val therapySession = TherapySessionInfo(
        patientId = 1,
        therapistId = 2,
        sessionDate = "24/06/2024",
        sessionTime = 1200,
        preSessionNotes = "Ni ganas de ir hoy a la sesión",
        postSessionNotes = "",
        sessionFeel = "3"
    )
    sessionHistoryList = listOf(therapySession)


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
                        SharedComponents().HalfCircleTop(title = "Historial de sesiones")
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
                                    "Aquí podras ver el historial de sesiones con tu terapeuta",
                                    "Clickeame para esconder mi diálogo"
                                )
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        TherapistNameTitle(sessionTherapist)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        TherapySessionHistoryTitle()
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        sessionHistoryList.forEach {
                            TherapySession(navController, therapySessionViewModel, it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TherapistNameTitle(sessionTherapist: TherapistInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "${sessionTherapist.name} ${sessionTherapist.lastName}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 1.dp, end = 1.dp),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = secondaryColor
            )
        )
    }
}

@Composable
fun TherapySessionHistoryTitle() {
    Column(modifier = Modifier.padding(horizontal = 25.dp)) {
        Text("Historial de sesiones", fontSize = 24.sp, color = secondaryColor)
        Divider(color = secondaryColor, thickness = 2.dp)
    }
}

@Composable
fun TherapySession(
    navController: NavController,
    therapySessionViewModel: TherapySessionViewModel,
    therapySessionInfo: TherapySessionInfo
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .weight(0.7f)
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    "${therapySessionInfo.sessionDate} - ${
                        therapySessionViewModel.addColonTime(
                            therapySessionInfo.sessionTime
                        )
                    }",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = secondaryColor,
                    fontSize = 24.sp
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(0.2f)
                    .padding(horizontal = 4.dp)
            ) {
                Text("Botón")
            }
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .weight(0.1f)
                    .padding(horizontal = 4.dp)
            ) {
                GoToTherapySession(navController, therapySessionViewModel, therapySessionInfo)
            }
        }
    }
}

@Composable
fun GoToTherapySession(
    navController: NavController,
    therapySessionViewModel: TherapySessionViewModel,
    therapySessionInfo: TherapySessionInfo
) {
    IconButton(
        onClick = {
            therapySessionViewModel.loadTherapySessionToEdit(therapySessionInfo)
            navController.navigate(Routes.TherapySessionScreen.route)
        },
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = "ir",
            tint = secondaryColor
        )
    }
}
