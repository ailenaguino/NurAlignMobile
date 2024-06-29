package com.losrobotines.nuralign.feature_weekly_summary.domain.usecases

import android.util.Log
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetWeeklyMedicationListInfoUseCase @Inject constructor(
    private val weeklySummaryProvider: WeeklySummaryProvider
) {

    suspend operator fun invoke(patientId: Short): List<MedicationInfo?> {
        val medicationList = mutableListOf<MedicationInfo?>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        val date = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        return weeklySummaryProvider.getMedicationListInfo(patientId)

    }
}