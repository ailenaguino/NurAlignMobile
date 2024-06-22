package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerProvider
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import javax.inject.Inject

class SaveMoodTrackerDataUseCase @Inject constructor(private val moodTrackerProvider: MoodTrackerProvider) {

    suspend operator fun invoke(moodTrackerInfo: MoodTrackerInfo): Result<Unit> {
        return try {
            moodTrackerProvider.saveMoodTrackerInfo(moodTrackerInfo)
            Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}