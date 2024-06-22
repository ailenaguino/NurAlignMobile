package com.losrobotines.nuralign.feature_achievements.domain

import com.losrobotines.nuralign.feature_achievements.domain.models.Achievement
import com.losrobotines.nuralign.feature_achievements.domain.models.Counter

interface AchievementRepository {
    suspend fun getUserAchievements():List<Achievement>
    suspend fun addAchievement(achievement: Achievement)
    suspend fun getTrackerCounter(tracker: String):Counter?
    suspend fun startCounter(counter: Counter)
    suspend fun addOneToCounter(tracker: String)
    suspend fun restartCounters()
}