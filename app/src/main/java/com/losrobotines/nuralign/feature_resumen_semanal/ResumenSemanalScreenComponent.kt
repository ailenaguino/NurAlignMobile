package com.losrobotines.nuralign.feature_resumen_semanal


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import com.losrobotines.nuralign.ui.theme.mainColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.ui.theme.secondaryColor

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodBarChartExample() {
    var isChecked by remember { mutableStateOf(false) }
    var isSleepChartVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Resumen semanal",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(80.dp))

        ToggleCard(
            text = if (isChecked) "Esconder" else "Ver Estados de ánimo de mi semana",
            isVisible = isChecked,
            onToggle = { isChecked = !isChecked }
        )

        ToggleCard(
            text = if (isSleepChartVisible) "Esconder Sueño" else "Ver estadisticas del Sueño de mi semana",
            isVisible = isSleepChartVisible,
            onToggle = { isSleepChartVisible = !isSleepChartVisible }
        )

        // AnimatedVisibility for MoodBarChartList
        AnimatedVisibility(visible = isChecked) {
            MoodBarChartList( getExampleMoodTrackerData())
        }

        // AnimatedVisibility for SleepChart
        AnimatedVisibility(visible = isSleepChartVisible) {
            SleepChart(getExampleSleepData())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToggleCard(
    text: String,
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = mainColor
        ),
        onClick = { onToggle() }
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    color = Color.White // Ensure text color contrasts with the background
                )
                Icon(
                    imageVector = if (isVisible) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp),
                    tint = Color.White // Ensure icon color contrasts with the background
                )
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoodBarChartList(moodData: List<MoodTrackerInfo>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(moodData) { moodInfo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                MoodBarChart(moodInfo)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = formatDate(moodInfo.effectiveDate),
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
                            withStyle(style = SpanStyle( color = secondaryColor)) {
                                append("Ánimo Elevado: ")
                            }
                            append(moodInfo.highestNote)
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle( color = secondaryColor)) {
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
            Spacer(modifier = Modifier.height(16.dp))
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
    val maxValue = values.maxOrNull() ?: 0
    val totalHeight = 150.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(120.dp)
            .height(totalHeight)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            values.forEachIndexed { index, value ->
                val barHeightFraction = if (maxValue > 0) (value + 1) / 6f else 0f
                val barHeight = if (barHeightFraction > 0) {
                    (totalHeight * barHeightFraction).coerceAtLeast(minBarHeight)
                } else {
                    minBarHeight
                }
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(barHeight)
                        .background(barColors[index])
                        .padding(bottom = 5.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepChart(sleepData: List<SleepInfo>) {
    val maxSleepHours = 16f // Máximo de horas de sueño
    val columnHeight = 40.dp // Altura de cada fila en dp
    val columnSpacing = 8.dp // Espacio entre filas en dp

    var selectedSleepInfo by remember { mutableStateOf<SleepInfo?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(columnSpacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Horas de sueño por paciente",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        items(sleepData.size) { index ->
            val sleepInfo = sleepData[index]
            val sleepHours = sleepInfo.sleepHours.toFloat()
            val normalizedSleepHours = if (sleepHours > maxSleepHours) maxSleepHours else sleepHours
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
                Text(formatDate(sleepData[index].effectiveDate))
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
                            paint = androidx.compose.ui.graphics.Paint().apply {
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

    if (isDialogVisible) {
        AlertDialog(
            onDismissRequest = { isDialogVisible = false },
            title = { Text("Detalles del Sueño") },
            text = {
                selectedSleepInfo?.let { info ->
                    Column {
                        Text(
                            "Fecha: ${formatDate(info.effectiveDate)}",
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.Black
                        )
                        Text(
                            "Horas de sueño: ${info.sleepHours}h",
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.Black
                        )
                        Text(
                            "Hora de acostarse: ${info.bedTime}",
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


@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(dateString, formatter)
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM")
    return date.format(outputFormatter)
}


private fun getExampleSleepData(): List<SleepInfo> {
    return listOf(
        SleepInfo(1, "2024-06-09", 7, "22:30", "No", "No", "Sí", "Dormí bien sin interrupciones."),
        SleepInfo(
            2,
            "2024-06-10",
            6,
            "23:00",
            "Sí",
            "Sí",
            "No",
            "Desperté varias veces durante la noche."
        ),
        SleepInfo(
            3,
            "2024-06-11",
            12,
            "21:45",
            "No",
            "No",
            "Sí",
            "Sueño reparador, me siento descansado."
        ),
        SleepInfo(
            4,
            "2024-06-12",
            5,
            "00:15",
            "Sí",
            "No",
            "No",
            "No pude conciliar el sueño fácilmente."
        ),
        SleepInfo(
            5,
            "2024-06-13",
            7,
            "22:00",
            "No",
            "Sí",
            "Sí",
            "Me desperté temprano, pero descansado."
        ),
        SleepInfo(
            6,
            "2024-06-14",
            6,
            "23:30",
            "No",
            "No",
            "Sí",
            "Sueño interrumpido por ruido exterior."
        ),
        SleepInfo(
            7,
            "2024-06-15",
            9,
            "21:00",
            "No",
            "No",
            "Sí",
            "Dormí profundamente y me siento muy bien."
        )
    )
}

private fun getExampleMoodTrackerData(): List<MoodTrackerInfo> {
    return listOf(
        MoodTrackerInfo(
            patientId = 1,
            effectiveDate = "2024-06-08",
            highestValue = "4",
            lowestValue = "1",
            highestNote = "Sentí bien por la mañana",
            lowestNote = "Cansado por la tarde",
            irritableValue = "1",
            irritableNote = "Ligeramente molesto",
            anxiousValue = "2",
            anxiousNote = "Ansiedad leve"
        ),
        MoodTrackerInfo(
            patientId = 1,
            effectiveDate = "2024-06-09",
            highestValue = "3",
            lowestValue = "0",
            highestNote = "Día productivo",
            lowestNote = "Tarde estresante",
            irritableValue = "4",
            irritableNote = "Muy irritable durante el trabajo",
            anxiousValue = "3",
            anxiousNote = "Ansiedad moderada"
        ),
        MoodTrackerInfo(
            patientId = 1,
            effectiveDate = "2024-06-10",
            highestValue = "0",
            lowestValue = "1",
            highestNote = "Mañana relajada",
            lowestNote = "Cansado por la tarde",
            irritableValue = "1",
            irritableNote = "Calmado la mayor parte del día",
            anxiousValue = "1",
            anxiousNote = "Ansiedad baja"
        ),
        MoodTrackerInfo(
            patientId = 1,
            effectiveDate = "2024-06-11",
            highestValue = "4",
            lowestValue = "2",
            highestNote = "Muy buen humor",
            lowestNote = "Ligeramente cansado por la tarde",
            irritableValue = "0",
            irritableNote = "No irritable",
            anxiousValue = "1",
            anxiousNote = "Ansiedad muy baja"
        ),
        MoodTrackerInfo(
            patientId = 1,
            effectiveDate = "2024-06-12",
            highestValue = "0",
            lowestValue = "1",
            highestNote = "Día promedio",
            lowestNote = "Cansado por la tarde",
            irritableValue = "4",
            irritableNote = "Muy irritable por la tarde",
            anxiousValue = "3",
            anxiousNote = "Ansiedad moderada"
        ),
        MoodTrackerInfo(
            patientId = 1,
            effectiveDate = "2024-06-13",
            highestValue = "4",
            lowestValue = "2",
            highestNote = "Sentí bien la mayor parte del día",
            lowestNote = "Baja energía por la tarde",
            irritableValue = "1",
            irritableNote = "Ligeramente molesto",
            anxiousValue = "1",
            anxiousNote = "Ansiedad leve"
        ),
        MoodTrackerInfo(
            patientId = 1,
            effectiveDate = "2024-06-14",
            highestValue = "3",
            lowestValue = "0",
            highestNote = "Buen humor en general",
            lowestNote = "Cansado por la tarde",
            irritableValue = "2",
            irritableNote = "Irritable durante el trayecto",
            anxiousValue = "2",
            anxiousNote = "Ansiedad moderada"
        )
    )
}