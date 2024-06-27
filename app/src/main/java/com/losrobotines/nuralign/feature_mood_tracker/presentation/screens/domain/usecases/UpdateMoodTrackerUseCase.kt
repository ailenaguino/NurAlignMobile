package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerProvider
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import javax.inject.Inject

class UpdateMoodTrackerUseCase @Inject constructor(private val provider: MoodTrackerProvider) {

    suspend operator fun invoke(moodTrackerInfo: MoodTrackerInfo): Result<Unit> {
        return try{
            provider.updateMoodTrackerInfo(moodTrackerInfo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}