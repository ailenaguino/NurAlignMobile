package com.losrobotines.nuralign.feature_routine.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="routine_table")
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "sleep_time")
    val sleepTime: String,
    @ColumnInfo(name ="activity")
    val activity: String,
    @ColumnInfo("activity_time")
    val activityTime: String
)




