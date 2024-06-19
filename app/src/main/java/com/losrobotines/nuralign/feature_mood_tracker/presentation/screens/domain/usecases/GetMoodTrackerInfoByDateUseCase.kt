package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases

import android.util.Log
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerProvider
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import javax.inject.Inject

class GetMoodTrackerInfoByDateUseCase @Inject constructor(private val moodTrackerProvider: MoodTrackerProvider) {

    suspend operator fun invoke(patientId: Int, date: String): MoodTrackerInfo? {
        return try {
            moodTrackerProvider.getTodaysTracker(patientId, date)
        } catch (e: Exception) {
            return null
        }
    }
}