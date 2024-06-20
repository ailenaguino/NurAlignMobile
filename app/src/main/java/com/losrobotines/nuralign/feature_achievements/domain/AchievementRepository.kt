package com.losrobotines.nuralign.feature_achievements.domain

import com.losrobotines.nuralign.feature_achievements.domain.models.Achievement
import com.losrobotines.nuralign.feature_achievements.domain.models.Counter

interface AchievementRepository {
    suspend fun getUserAchivements():List<Achievement>
    suspend fun getTrackerCounter(tracker: String):Counter?
    suspend fun startCounter(counter: Counter)
    suspend fun addOneToCounter(tracker: String)
}