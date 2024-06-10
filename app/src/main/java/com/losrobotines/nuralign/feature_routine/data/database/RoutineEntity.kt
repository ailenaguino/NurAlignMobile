package com.losrobotines.nuralign.feature_routine.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="routine_table")
data class RoutineEntity(
    @PrimaryKey()
    var id: Int = 0,
    @ColumnInfo(name = "sleep_time")
    var sleepTime: String,
    @ColumnInfo(name = "activity")
    var activity: String,
    @ColumnInfo(name = "activity_time")
    var activityTime: String,
    @ColumnInfo(name = "activity_days")
    var activityDays: List<String>
)
