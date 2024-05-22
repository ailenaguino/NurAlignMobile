package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain

import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo

interface MoodTrackerRepository {

    suspend fun saveMoodTrackerInfo(moodTrackerInfo: MoodTrackerInfo)
    suspend fun getMoodTrackerInfo(patientId: Int, date: String): MoodTrackerInfo?
}