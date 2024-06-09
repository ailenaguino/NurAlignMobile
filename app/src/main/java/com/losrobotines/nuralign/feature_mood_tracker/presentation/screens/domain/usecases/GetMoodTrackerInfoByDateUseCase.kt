package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerProvider
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import javax.inject.Inject

class GetMoodTrackerInfoByDateUseCase @Inject constructor(private val moodTrackerProvider: MoodTrackerProvider) {

    suspend operator fun invoke(patientId : Int, date: String) : MoodTrackerInfo?{
        return moodTrackerProvider.getTodaysTracker(patientId, date)
    }
}