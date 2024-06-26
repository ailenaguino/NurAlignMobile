package com.losrobotines.nuralign.feature_resumen_semanal.domain.usecases

import javax.inject.Inject
import kotlin.math.roundToInt

class CalculateWeeklyMoodAveragesUseCase @Inject constructor(
    private val getWeeklyMoodTrackerInfoUseCase: GetWeeklyMoodTrackerInfoUseCase
) {

    suspend operator fun invoke(patientId: Short): MoodTrackerAveragesLabels? {
        val moodTrackerInfoList = getWeeklyMoodTrackerInfoUseCase(patientId)
        val validMoodTrackerInfoList = moodTrackerInfoList.filterNotNull()

        if (validMoodTrackerInfoList.isEmpty()) {
            return null
        }

        val highestValues = validMoodTrackerInfoList.map { it.highestValue.toInt() }
        val lowestValues = validMoodTrackerInfoList.map { it.lowestValue.toInt() }
        val irritableValues = validMoodTrackerInfoList.map { it.irritableValue.toInt() }
        val anxiousValues = validMoodTrackerInfoList.map { it.anxiousValue.toInt() }

        val averageHighestValue = highestValues.average().roundToInt()
        val averageLowestValue = lowestValues.average().roundToInt()
        val averageIrritableValue = irritableValues.average().roundToInt()
        val averageAnxiousValue = anxiousValues.average().roundToInt()

        return MoodTrackerAveragesLabels(
            mapValueToLabel(averageHighestValue),
            mapValueToLabel(averageLowestValue),
            mapValueToLabel(averageIrritableValue),
            mapValueToLabel(averageAnxiousValue)
        )
    }

    private fun mapValueToLabel(value: Int): String {
        return when (value) {
            0 -> "Nulo"
            1 -> "Leve"
            2 -> "Moderado"
            3 -> "Alto"
            4 -> "Severo"
            else -> "Nulo"
        }
    }
}

data class MoodTrackerAveragesLabels(
    val highestValueLabel: String,
    val lowestValueLabel: String,
    val irritableValueLabel: String,
    val anxiousValueLabel: String
)