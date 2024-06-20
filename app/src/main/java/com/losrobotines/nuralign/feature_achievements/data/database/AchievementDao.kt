package com.losrobotines.nuralign.feature_achievements.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.losrobotines.nuralign.feature_achievements.data.database.entities.AchievementEntity

@Dao
interface AchievementDao {

    @Query("SELECT * FROM achievement_table")
    suspend fun getAllAchievements():List<AchievementEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievementEntity: AchievementEntity)
}