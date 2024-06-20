package com.losrobotines.nuralign.feature_achievements.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_achievements.domain.models.Counter
import com.losrobotines.nuralign.feature_achievements.domain.usecases.AddOneToCounterUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.GetCounterUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.SendNotificationIfNeededUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.StartCounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val getCounterUseCase: GetCounterUseCase,
    private val startCounterUseCase: StartCounterUseCase,
    private val addOneToCounterUseCase: AddOneToCounterUseCase,
    private val sendNotificationIfNeededUseCase: SendNotificationIfNeededUseCase
) : ViewModel() {

    object TrackerConstants {
        const val MOOD_TRACKER = "mood"
        const val SLEEP_TRACKER = "sleep"
        const val THERAPY_TRACKER = "therapy"
        const val MEDICATION_TRACKER = "medication"
    }

    fun moodTrackerIsSaved() {
        viewModelScope.launch {
            val counter = getCounterUseCase(TrackerConstants.MOOD_TRACKER)
            if (counter == null) {
                startCounterUseCase(TrackerConstants.MOOD_TRACKER)
            } else {
                addOneToCounterUseCase(TrackerConstants.MOOD_TRACKER)
                sendNotificationIfNeededUseCase(Counter(TrackerConstants.MOOD_TRACKER, counter))
            }
        }
    }

}