package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.data.RoutineRepositoryDatabase
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import javax.inject.Inject

class LoadRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepositoryDatabase
) {

    suspend fun execute(): RoutineEntity {
        return routineRepository.getRoutine()
    }
}