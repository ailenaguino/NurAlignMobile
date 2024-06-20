package com.losrobotines.nuralign.feature_routine.data.database

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.losrobotines.nuralign.feature_routine.domain.models.Activity


@Database(entities = [RoutineEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RoutineDatabase : RoomDatabase() {

    abstract fun routineDao(): RoutineDao
}



class Converters {
    @TypeConverter
    fun fromActivityList(value: List<Activity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Activity>>() {}.type
        val json = gson.toJson(value, type)
        Log.d("Converters", "Converting from ActivityList to JSON: $json")
        return json
    }

    @TypeConverter
    fun toActivityList(value: String): List<Activity> {
        val gson = Gson()
        val type = object : TypeToken<List<Activity>>() {}.type
        val activities = gson.fromJson<List<Activity>>(value, type)
        Log.d("Converters", "Converting from JSON to ActivityList: $activities")
        return activities
    }
}