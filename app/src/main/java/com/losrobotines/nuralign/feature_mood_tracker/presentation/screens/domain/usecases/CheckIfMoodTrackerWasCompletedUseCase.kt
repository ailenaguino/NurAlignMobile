package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerProvider
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class CheckIfMoodTrackerWasCompletedUseCase @Inject constructor(private val moodTrackerProvider: MoodTrackerProvider) {

    suspend operator fun invoke(domainId: Int): Boolean{
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.format(Date())
        val result = moodTrackerProvider.getTodaysTracker(domainId,date)
        return (result != null)
    }
}