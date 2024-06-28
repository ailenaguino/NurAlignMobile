package com.losrobotines.nuralign.feature_weekly_summary.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_weekly_summary.presentation.WeeklySummaryViewModel
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklySummaryMedicationTracker(weeklySummaryViewModel: WeeklySummaryViewModel) {
    val medicationList by weeklySummaryViewModel.medicationList.collectAsState()
    val medicationTrackerList by weeklySummaryViewModel.medicationTrackerInfoList.collectAsState()

    val medicationTrackerInfoList = listOf(
        MedicationTrackerInfo(1, "2024-06-23", "Y"),
        MedicationTrackerInfo(1, "2024-06-22", "N"),
        MedicationTrackerInfo(1, "2024-06-21", "Y"),
        MedicationTrackerInfo(1, "2024-06-20", "Y"),
        MedicationTrackerInfo(1, "2024-06-19", "N"),
        MedicationTrackerInfo(1, "2024-06-18", "Y"),
        MedicationTrackerInfo(1, "2024-06-17", "Y"),

        MedicationTrackerInfo(2, "2024-06-23", "N"),
        MedicationTrackerInfo(2, "2024-06-22", "Y"),
        MedicationTrackerInfo(2, "2024-06-21", "Y"),
        MedicationTrackerInfo(2, "2024-06-20", "N"),
        MedicationTrackerInfo(2, "2024-06-19", "Y"),
        MedicationTrackerInfo(2, "2024-06-18", "Y"),
        MedicationTrackerInfo(2, "2024-06-17", "N"),

        MedicationTrackerInfo(3, "2024-06-23", "Y"),
        MedicationTrackerInfo(3, "2024-06-22", "Y"),
        MedicationTrackerInfo(3, "2024-06-21", "N"),
        MedicationTrackerInfo(3, "2024-06-20", "Y"),
        MedicationTrackerInfo(3, "2024-06-19", "Y"),
        MedicationTrackerInfo(3, "2024-06-18", "N"),
        MedicationTrackerInfo(3, "2024-06-17", "Y")
    )

    val medicationInfoList = listOf(
        MedicationInfo(1, 1, "Medication A", 50, "Y"),
        MedicationInfo(2, 1, "Medication B", 100, "N"),
        MedicationInfo(3, 1, "Medication C", 75, "Y"),
    )

    val organizedData = organizeMedicationData(medicationTrackerList, medicationList)

    Column() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1)
        ) {
            item {
                SharedComponents().HalfCircleTop(title = "     Estadística de la\n  toma de medicacion")
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
                            "Resumen de la toma de medicación de tu última semana",
                            "Clickeame para esconder mi diálogo"
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        MedicationChart(organizedData, weeklySummaryViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MedicationChart(
    organizedData: Map<String, List<Pair<String, Boolean>>>,
    weeklySummaryViewModel: WeeklySummaryViewModel
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            organizedData.forEach { (date, medicationStatusList) ->
                Text(
                    text = weeklySummaryViewModel.formatDate(date),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    medicationStatusList.forEach { (name, taken) ->
                        MedicationStatusItem(name = name, taken = taken)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun MedicationStatusItem(
    name: String,
    taken: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = name,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.width(10.dp))
        if (taken) {
            Image(
                painterResource(id = R.drawable.check),
                contentDescription = "Tomado",
                modifier = Modifier.size(30.dp)
            )
        } else {
            Image(
                painterResource(id = R.drawable.error),
                contentDescription = "No Tomado",
                modifier = Modifier.size(30.dp))
        }

    }
}
fun organizeMedicationData(
    medicationTrackerList: List<MedicationTrackerInfo?>,
    medicationList: List<MedicationInfo?>
): Map<String, List<Pair<String, Boolean>>> {
    val organizedData = mutableMapOf<String, List<Pair<String, Boolean>>>()

    medicationTrackerList.forEach { tracker ->
        if (tracker == null) return@forEach
        val date = tracker.effectiveDate
        val medicationsForDate =
            medicationList.filter { it?.patientMedicationId == tracker.patientMedicationId }
        medicationsForDate.forEach { medication ->
            val taken = tracker.takenFlag == "Y"
            val medicationStatus = Pair(medication?.medicationName, taken)
            val dailyList = organizedData.getOrDefault(date, emptyList())
            organizedData[date] = (dailyList + medicationStatus) as List<Pair<String, Boolean>>
        }
    }

    return organizedData
}