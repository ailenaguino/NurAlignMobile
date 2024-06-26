package com.losrobotines.nuralign.feature_resumen_semanal.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_resumen_semanal.presentation.WeeklySummaryViewModel
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor


@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklySummaryMoodTracker(weeklySummaryViewModel: WeeklySummaryViewModel) {
    val moodTrackerInfoList by weeklySummaryViewModel.moodTrackerInfoList.collectAsState()
    val isLoading by weeklySummaryViewModel.isLoading.collectAsState()
    val moodAveragesLabels by weeklySummaryViewModel.moodAveragesLabels.collectAsState()


    Column() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1)
        ) {
            item {
                SharedComponents().HalfCircleTop(title = "  Estadística del\nestado del ánimo")
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
                    val moodMessage = "En tu semana tuviste un promedio de ánimo:\nElevado: ${moodAveragesLabels?.highestValueLabel},"
                    val moodMessage2 ="Deprimido: ${moodAveragesLabels?.lowestValueLabel},\nIrritable: ${moodAveragesLabels?.irritableValueLabel},\nAnsioso: ${moodAveragesLabels?.anxiousValueLabel}"

                    SharedComponents().fabCompanion(
                        listOf(
                            moodMessage,
                            moodMessage2,
                            "Clickeame para esconder mi diálogo"
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))


        Linea()


        Spacer(modifier = Modifier.height(10.dp))


        MoodBarChartList(moodTrackerInfoList,weeklySummaryViewModel)
    }


}


@SuppressLint("SuspiciousIndentation")
@Composable
fun Linea() {
    val lineaModifier = Modifier
        .fillMaxWidth()
        .height(4.dp)
    Box(
        modifier = lineaModifier
            .background(mainColor)
    )

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoodBarChartList(
    moodData: List<MoodTrackerInfo?>,
    weeklySummaryViewModel: WeeklySummaryViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(moodData) { moodInfo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                if (moodInfo != null) {
                    MoodBarChart(moodInfo)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = weeklySummaryViewModel.formatDate(moodInfo!!.effectiveDate),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = secondaryColor)) {
                                append("Ánimo Elevado: ")
                            }
                            append(moodInfo.highestNote)
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = secondaryColor)) {
                                append("Ánimo Deprimido: ")
                            }
                            append(moodInfo.lowestNote)
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = secondaryColor)) {
                                append("Ánimo Irritable: ")
                            }
                            append(moodInfo.irritableNote)
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = secondaryColor)) {
                                append("Ánimo Ansioso: ")
                            }
                            append(moodInfo.anxiousNote)
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Linea()
        }
    }
}

@Composable
fun MoodBarChart(moodInfo: MoodTrackerInfo) {
    val barColors = listOf(
        Color(0xff91c776),
        Color(0xff9ebadc),
        Color(0xffdec278),
        Color(0xffc381ba)
    )
    val barLabels = listOf("Elevado", "Deprimido", "Irritable", "Ansioso")
    val values = listOf(
        moodInfo.highestValue.toIntOrNull() ?: 0,
        moodInfo.lowestValue.toIntOrNull() ?: 0,
        moodInfo.irritableValue.toIntOrNull() ?: 0,
        moodInfo.anxiousValue.toIntOrNull() ?: 0
    )

    val minBarHeight = 20.dp
    val totalHeight = 180.dp

    val labels = listOf("Severo", "Alto", "Moderado", "Leve", "Nulo")

    Row(
        modifier = Modifier
            .height(totalHeight),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.height(totalHeight)
        ) {
            labels.forEach { label ->
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(150.dp)
                .height(totalHeight)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                values.forEachIndexed { index, value ->
                    val barHeightFraction =
                        if (labels.size > 0) value / (labels.size - 1).toFloat() else 0f
                    val barHeight = if (barHeightFraction > 0) {
                        (totalHeight * barHeightFraction).coerceAtLeast(minBarHeight)
                    } else {
                        minBarHeight
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(start = 2.dp)
                                .width(25.dp)
                                .height(barHeight)
                                .background(barColors[index])
                        )
                    }
                }
            }
        }
    }
}

