package com.losrobotines.nuralign.feature_resumen_semanal.presentation.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import com.losrobotines.nuralign.ui.theme.mainColor
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.losrobotines.nuralign.feature_resumen_semanal.presentation.WeeklySummaryViewModel
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklySummaryScreenComponent(
    weeklySummaryViewModel: WeeklySummaryViewModel,
    navController: NavHostController
) {
    var isChecked by remember { mutableStateOf(false) }
    var isSleepChartVisible by remember { mutableStateOf(false) }
    val moodTrackerInfoList by weeklySummaryViewModel.moodTrackerInfoList.collectAsState()
    val sleepTrackerInfoList by weeklySummaryViewModel.sleepTrackerInfoList.collectAsState()
    val isLoading by weeklySummaryViewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1)
                ) {
                    item {
                        SharedComponents().HalfCircleTop(title = "Resumen Semanal")
                    }
                }


                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    item {
                        if (moodTrackerInfoList.isEmpty() && sleepTrackerInfoList.isEmpty()) {
                            Text(
                                text = "Del 15/6 al 22/6",
                                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        } else {
                            val startDate = moodTrackerInfoList.lastOrNull()?.effectiveDate
                            val endDate = moodTrackerInfoList.firstOrNull()?.effectiveDate

                            if (startDate != null && endDate != null) {
                                Text(
                                    text = "Resumen Del ${
                                        weeklySummaryViewModel.formatDateString(startDate)
                                    } al ${
                                        weeklySummaryViewModel.formatDateString(endDate)
                                    }",
                                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            } else {
                                Text(
                                    text = "No data available",
                                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
                Row() {

                    Button(onClick = { navController.navigate(Routes.WeeklySummaryMoodTracker.route) }) {
                        Text("Ver Estados de\n   ánimo de\n   mi semana")

                    }




                    Button(onClick = { navController.navigate(Routes.WeeklySummarySleepTracker.route) }) {
                        Text("Ver estadísticas\n      del Sueño\n  de mi semana")

                    }

                }
            }
        }
    }
}

