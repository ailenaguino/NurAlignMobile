package com.losrobotines.nuralign.feature_routine.domain

import com.losrobotines.nuralign.feature_routine.domain.models.Routine

interface RoutineProvider {

    suspend fun addRoutine(routine: Routine)

    suspend fun getRoutine(): Routine

}