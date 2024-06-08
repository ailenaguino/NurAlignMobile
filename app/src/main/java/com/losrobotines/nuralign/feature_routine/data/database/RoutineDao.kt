package com.losrobotines.nuralign.feature_routine.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RoutineDao {

    @Query("SELECT * FROM routine_table")
    fun getAll(): List<RoutineEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(routine: RoutineEntity)


    @Delete
    fun delete(routine: RoutineEntity)
}