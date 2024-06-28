package com.losrobotines.nuralign.feature_weekly_summary.presentation.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Bedtime
import androidx.compose.material.icons.twotone.Medication
import androidx.compose.material.icons.twotone.Mood
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.losrobotines.nuralign.ui.theme.mainColor
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_weekly_summary.presentation.WeeklySummaryViewModel
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.green
import com.losrobotines.nuralign.ui.theme.purple
import com.losrobotines.nuralign.ui.theme.secondaryColor
import com.losrobotines.nuralign.ui.theme.turquoise

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklySummaryScreenComponent(
    weeklySummaryViewModel: WeeklySummaryViewModel,
    navController: NavHostController
) {
    var isChecked by remember { mutableStateOf(false) }
    var isSleepChartVisible by remember { mutableStateOf(false) }
    val moodTrackerInfoList by weeklySummaryViewModel.moodTrackerInfoList.collectAsState()
    val isLoading by weeklySummaryViewModel.isLoading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        weeklySummaryViewModel.updateData()

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
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (isLoading) {
                    LoadingScreen()
                } else {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(1)
                        ) {
                            item {
                                SharedComponents().HalfCircleTop(title = "Resumen Semanal")
                            }
                            item {
                                RobotinWeeklySummaryScreen()
                            }

                        }
                        ScreenInfo(navController)
                    }
                }
            }
        }
        SnackbarError(weeklySummaryViewModel, snackbarHostState)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SnackbarError(
    weeklySummaryViewModel: WeeklySummaryViewModel,
    snackbarHostState: SnackbarHostState
) {
    val errorMessage by weeklySummaryViewModel.errorMessage.observeAsState()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            weeklySummaryViewModel.clearErrorMessage()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenInfo(navController: NavHostController) {
    Spacer(modifier = Modifier.height(40.dp))
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ElevatedInfoCard(
                icon = Icons.TwoTone.Mood,
                contentDescription = "Seguimiento del ánimo",
                tint = turquoise,
                text = "Resumen del estado de ánimo",
                onClick = {
                    navController.navigate(Routes.WeeklySummaryMoodTracker.route)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ElevatedInfoCard(
                icon = Icons.TwoTone.Bedtime,
                contentDescription = "Seguimiento del sueño",
                tint = purple,
                text = "Resumen del sueño",
                onClick = {
                    navController.navigate(Routes.WeeklySummarySleepTracker.route)
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedInfoCard(
            icon = Icons.TwoTone.Medication,
            contentDescription = "Seguimiento del la medicacion",
            tint = green,
            text = "Resumen de la medicacion",
            onClick = {
                navController.navigate(Routes.WeeklySummaryMedicationTracker.route)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElevatedInfoCard(
    icon: ImageVector,
    contentDescription: String,
    tint: Color,
    text: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .size(width = 120.dp, height = 140.dp)
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier
                .size(60.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )
        androidx.compose.material3.Text(
            text = text,
            color = tint,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}
@Composable
private fun RobotinWeeklySummaryScreen(
) {
    LargeFloatingActionButton(
        onClick = {},
        shape = RoundedCornerShape(10.dp),
        containerColor = mainColor,
        modifier = Modifier.padding(start = 8.dp, end = 9.dp),
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 7.dp
        )
    ) {
        SharedComponents().fabCompanion(
            listOf(
                "Te dejo el resumen de tu última semana",
                "Elegí que resumen querés ver",
                "Clickeame para esconder mi diálogo"
            )
        )
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

