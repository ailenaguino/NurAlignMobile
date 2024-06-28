package com.losrobotines.nuralign.feature_home.domain.usecases

import com.losrobotines.nuralign.feature_medication.domain.usecases.tracker.CheckIfMedicationTrackerWasCompletedUseCase
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases.CheckIfMoodTrackerWasCompletedUseCase
import com.losrobotines.nuralign.feature_sleep.domain.usecases.CheckIfSleepTrackerWasCompletedUseCase
import com.losrobotines.nuralign.navigation.Routes
import javax.inject.Inject

class CheckNextTrackerToBeCompletedUseCase @Inject constructor(
    private val moodTrackerUseCase: CheckIfMoodTrackerWasCompletedUseCase,
    private val sleepTrackerUseCase: CheckIfSleepTrackerWasCompletedUseCase,
    private val medicationTrackerUseCase: CheckIfMedicationTrackerWasCompletedUseCase
) {

    suspend operator fun invoke(domainId: Int): String {
        return if (!moodTrackerUseCase(domainId)){
            Routes.MoodTrackerScreen.route
        } else if(!sleepTrackerUseCase(domainId)) {
            Routes.SleepTrackerScreen.route
        } else if(!medicationTrackerUseCase(domainId)){
            Routes.MedicationTrackerScreen.route
        } else {
            Routes.HomeScreen.route
        }
    }
}