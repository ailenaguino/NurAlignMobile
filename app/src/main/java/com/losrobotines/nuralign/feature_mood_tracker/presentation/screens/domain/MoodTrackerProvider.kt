package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo

interface MoodTrackerProvider {

    suspend fun saveMoodTrackerInfo(moodTrackerInfo: MoodTrackerInfo) : Boolean
    suspend fun getMoodTrackerInfo(patientId: Int,  effectiveDate: String): MoodTrackerInfo?
    suspend fun updateMoodTrackerInfo(moodTrackerInfo: MoodTrackerInfo): Boolean
}