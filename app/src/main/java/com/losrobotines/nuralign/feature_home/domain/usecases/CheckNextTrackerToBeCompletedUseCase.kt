package com.losrobotines.nuralign.feature_home.domain.usecases

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases.CheckIfMoodTrackerWasCompletedUseCase
import com.losrobotines.nuralign.navigation.Routes
import javax.inject.Inject

class CheckNextTrackerToBeCompletedUseCase @Inject constructor(private val moodTrackerUseCase: CheckIfMoodTrackerWasCompletedUseCase) {

    suspend operator fun invoke(domainId: Int): String {
        if (!moodTrackerUseCase(domainId)){
            return Routes.MoodTrackerScreen.route
        } else {
            return Routes.HomeScreen.route
        }
    }
}