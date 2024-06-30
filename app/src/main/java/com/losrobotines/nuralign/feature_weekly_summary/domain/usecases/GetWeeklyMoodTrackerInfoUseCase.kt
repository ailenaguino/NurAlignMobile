package com.losrobotines.nuralign.feature_weekly_summary.domain.usecases

import com.losrobotines.nuralign.feature_mood_tracker.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetWeeklyMoodTrackerInfoUseCase @Inject constructor(
    private val weeklySummaryProvider: WeeklySummaryProvider
) {

    suspend operator fun invoke(patientId: Short): List<MoodTrackerInfo?> {
        val trackerList = mutableListOf<MoodTrackerInfo?>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        for (i in 0 until 7) {
            val date = dateFormat.format(calendar.time)
            val trackerInfo = weeklySummaryProvider.getMoodTracker(patientId, date)
            trackerList.add(trackerInfo)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }

        return trackerList
    }
}
