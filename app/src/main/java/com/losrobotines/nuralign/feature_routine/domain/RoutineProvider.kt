package com.losrobotines.nuralign.feature_routine.domain

interface RoutineProvider {

    suspend fun addRoutine(routine: Routine)

    suspend fun getRoutine(): Routine

}