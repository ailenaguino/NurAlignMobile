package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.data.RoutineRepositoryDatabase
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import javax.inject.Inject

class SaveRoutineUseCase @Inject constructor(private val routineRepository: RoutineRepositoryDatabase) {

    suspend fun execute(routine: RoutineEntity) {
        routineRepository.addRoutine(routine)
    }
}