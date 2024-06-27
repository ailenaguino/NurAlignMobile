package com.losrobotines.nuralign.feature_weekly_summary.domain.usecases

import android.util.Log
import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetWeeklySleepTrackerInfoUseCase @Inject constructor(
    private val weeklySummaryProvider: WeeklySummaryProvider
) {
    suspend operator fun invoke(patientId: Short): List<SleepInfo?> {
        val trackerList = mutableListOf<SleepInfo?>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        for (i in 0 until 7) {
            val date = dateFormat.format(calendar.time)
            Log.d("SleepTrackerUseCase", "Obteniendo datos para la fecha $date")
            val trackerInfo = weeklySummaryProvider.getSleepTracker(patientId, date)
            trackerList.add(trackerInfo)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }
        return trackerList
    }
}
