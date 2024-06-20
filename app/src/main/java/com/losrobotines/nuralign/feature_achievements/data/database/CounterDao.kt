package com.losrobotines.nuralign.feature_achievements.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.losrobotines.nuralign.feature_achievements.data.database.entities.CounterEntity

@Dao
interface CounterDao {

    @Query("SELECT * FROM counter_table WHERE tracker LIKE :tracker")
    suspend fun getCounterFromTracker(tracker: String):CounterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun startCounter(counterEntity: CounterEntity)

    @Query("UPDATE counter_table SET counter = counter+1 WHERE tracker LIKE :tracker")
    suspend fun updateCounter(tracker: String)
}