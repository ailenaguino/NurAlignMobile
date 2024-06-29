package com.losrobotines.nuralign.feature_weekly_summary.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.feature_weekly_summary.presentation.WeeklySummaryViewModel
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklySummarySleepTracker(weeklySummaryViewModel: WeeklySummaryViewModel) {
    val sleepTrackerInfoList by weeklySummaryViewModel.sleepTrackerInfoList.collectAsState()

    Column() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1)
        ) {
            item {
                SharedComponents().HalfCircleTop(title = "Estadística  del Sueño")
            }
            item {
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
                            "Esta semana dormiste un promedio de ${weeklySummaryViewModel.getAverageSleepHours()} horas por dia",
                            "Clickeame para esconder mi diálogo"
                        )
                    )
                }
            }
        }

        SleepChart(sleepTrackerInfoList,weeklySummaryViewModel)


    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepChart(sleepData: List<SleepInfo?>, weeklySummaryViewModel: WeeklySummaryViewModel) {
    val maxSleepHours = 24f // Máximo de horas de sueño
    val columnHeight = 40.dp // Altura de cada fila en dp
    val columnSpacing = 8.dp // Espacio entre filas en dp

    var selectedSleepInfo by remember { mutableStateOf<SleepInfo?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Horas de sueño por paciente",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(columnSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(sleepData.size) { index ->
                val sleepInfo = sleepData[index]
                if (sleepInfo != null) {
                    val sleepHours = sleepInfo.sleepHours.toFloat()
                    val normalizedSleepHours =
                        if (sleepHours > maxSleepHours) maxSleepHours else sleepHours
                    val columnWidthFraction = normalizedSleepHours / maxSleepHours

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(columnHeight)
                            .fillMaxWidth()
                            .clickable {
                                selectedSleepInfo = sleepInfo
                                isDialogVisible = true
                            }
                    ) {
                        Text(weeklySummaryViewModel.formatDate(sleepData[index]!!.effectiveDate))
                        Spacer(modifier = Modifier.width(6.dp))
                        Canvas(
                            modifier = Modifier
                                .weight(1f)
                                .height(20.dp)
                        ) {
                            val columnWidth = size.width
                            drawIntoCanvas { canvas ->
                                val right = columnWidth * columnWidthFraction
                                canvas.drawRoundRect(
                                    0f,
                                    0f,
                                    right,
                                    size.height,
                                    16f,
                                    16f,
                                    paint = Paint().apply {
                                        color = secondaryColor
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${sleepInfo.sleepHours}h",
                            fontSize = 14.sp,
                            color = secondaryColor
                        )
                    }
                }
            }
        }
    }

    if (isDialogVisible) {
        AlertDialog(
            onDismissRequest = { isDialogVisible = false },
            title = { Text("Detalles del Sueño") },
            text = {
                selectedSleepInfo?.let { info ->
                    Column {
                        Text(
                            "Fecha: ${weeklySummaryViewModel.formatDate(info.effectiveDate)}",
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.Black
                        )
                        Text(
                            "Horas de sueño: ${info.sleepHours}h",
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.Black
                        )
                        Text(
                            "Hora de acostarse: ${info.bedTime.toHourMinuteFormat()}",
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.Black
                        )
                        TextField(
                            value = info.sleepNotes,
                            onValueChange = {},
                            label = {
                                Text(
                                    "Notas adicionales",
                                    color = secondaryColor
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Start),
                            enabled = false,
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.LightGray,
                                focusedLabelColor = secondaryColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledTextColor = Color.Black
                            ),
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { isDialogVisible = false },
                    colors = ButtonDefaults.buttonColors(containerColor = mainColor)
                ) {
                    Text("Cerrar", color = Color.White)
                }
            }
        )
    }
}



fun String.toHourMinuteFormat(): String {
    return if (this.length in 3..4) {
        val hours = this.substring(0, this.length - 2).padStart(2, '0')
        val minutes = this.substring(this.length - 2).padStart(2, '0')
        "$hours:$minutes"
    } else {
        this
    }
}

