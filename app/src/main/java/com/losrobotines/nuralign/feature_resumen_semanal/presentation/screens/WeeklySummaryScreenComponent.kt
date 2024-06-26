package com.losrobotines.nuralign.feature_resumen_semanal.presentation.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.losrobotines.nuralign.feature_resumen_semanal.presentation.WeeklySummaryViewModel
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.secondaryColor

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
                    item {
                        val startDate = moodTrackerInfoList.lastOrNull()?.effectiveDate
                        val endDate = moodTrackerInfoList.firstOrNull()?.effectiveDate
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
                                    "Te dejo el resumen del ${
                                        startDate?.let { weeklySummaryViewModel.formatDateString(it) }
                                    } al ${
                                        endDate?.let { weeklySummaryViewModel.formatDateString(it) }
                                    }",
                                    "Clickeame para esconder mi diálogo"
                                )
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(40.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .width(160.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .clickable { navController.navigate(Routes.WeeklySummaryMoodTracker.route) },
                        contentColor = Color.White,
                        backgroundColor = secondaryColor
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Resumen del estado de ánimo",
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .width(160.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .clickable { navController.navigate(Routes.WeeklySummarySleepTracker.route) },
                        contentColor = Color.White,
                        backgroundColor = secondaryColor
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Resumen del sueño",
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .clickable { navController.navigate(Routes.WeeklySummaryMedicationTracker.route) },
                        contentColor = Color.White,
                        backgroundColor = secondaryColor
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Estadísticas de medicación",
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

}

