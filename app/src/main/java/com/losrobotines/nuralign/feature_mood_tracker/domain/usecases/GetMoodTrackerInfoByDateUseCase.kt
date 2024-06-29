package com.losrobotines.nuralign.feature_mood_tracker.domain.usecases

import com.losrobotines.nuralign.feature_mood_tracker.domain.MoodTrackerProvider
import com.losrobotines.nuralign.feature_mood_tracker.domain.models.MoodTrackerInfo
import javax.inject.Inject

class GetMoodTrackerInfoByDateUseCase @Inject constructor(private val moodTrackerProvider: MoodTrackerProvider) {

    suspend operator fun invoke(patientId: Int, date: String): MoodTrackerInfo? {
        return try {
            moodTrackerProvider.getMoodTrackerInfo(patientId, date)
        } catch (e: Exception) {
            return null
        }
    }
}