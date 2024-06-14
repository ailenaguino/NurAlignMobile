package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo

interface MoodTrackerProvider {

    suspend fun saveMoodTrackerInfo(moodTrackerInfo: MoodTrackerInfo) : Boolean
    suspend fun getMoodTrackerInfo(patientId: Int): MoodTrackerInfo?
    suspend fun getTodaysTracker(patientId: Int, date: String): MoodTrackerInfo?
}