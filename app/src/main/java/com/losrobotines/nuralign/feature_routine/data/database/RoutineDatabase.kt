package com.losrobotines.nuralign.feature_routine.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [RoutineEntity::class], version = 1)
abstract class RoutineDatabase : RoomDatabase() {

    abstract fun routineDao(): RoutineDao
}