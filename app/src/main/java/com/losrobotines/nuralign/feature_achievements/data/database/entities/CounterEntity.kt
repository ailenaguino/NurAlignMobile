package com.losrobotines.nuralign.feature_achievements.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "counter_table")
data class CounterEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "tracker") val tracker: String,
    @ColumnInfo(name = "counter") val counter: Int = 0
)