package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerProvider
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class CheckIfMoodTrackerWasCompletedUseCase @Inject constructor(private val moodTrackerProvider: MoodTrackerProvider) {

    suspend operator fun invoke(domainId: Int): Boolean{
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val result = moodTrackerProvider.getMoodTrackerInfo(domainId,date)
        return (result != null)
    }
}