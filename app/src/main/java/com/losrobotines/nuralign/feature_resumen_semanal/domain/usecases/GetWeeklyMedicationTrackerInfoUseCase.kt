package com.losrobotines.nuralign.feature_resumen_semanal.domain.usecases

import android.util.Log
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_resumen_semanal.domain.WeeklySummaryProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetWeeklyMedicationTrackerInfoUseCase @Inject constructor(
    private val weeklySummaryProvider: WeeklySummaryProvider
) {

    suspend operator fun invoke(patientId: Short): List<MedicationTrackerInfo?> {
        val trackerList = mutableListOf<MedicationTrackerInfo?>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        for (i in 0 until 7) {
            val date = dateFormat.format(calendar.time)
            Log.d("MedicationTrackerUseCase", "Obteniendo datos para la fecha $date")
            val trackerInfo = weeklySummaryProvider.getMedicationTracker(patientId, date)
            trackerList.add(trackerInfo)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }

        return trackerList
    }
}