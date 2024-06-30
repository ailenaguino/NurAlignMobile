package com.losrobotines.nuralign.feature_routine.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.losrobotines.nuralign.feature_routine.domain.models.Activity

@Entity(tableName="routine_table")
data class RoutineEntity(
    @PrimaryKey() val id: Int = 0,
    @ColumnInfo(name = "sleep_time")
    val sleepTime: String,
    @ColumnInfo(name = "activity_list")
    val activities: List<Activity>
)