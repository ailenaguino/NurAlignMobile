package com.losrobotines.nuralign.feature_routine.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Database(entities = [RoutineEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RoutineDatabase : RoomDatabase() {

    abstract fun routineDao(): RoutineDao
}



class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}