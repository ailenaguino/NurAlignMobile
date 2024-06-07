package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.data.RoutineRepositoryDatabase
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import com.losrobotines.nuralign.feature_routine.domain.Routine
import javax.inject.Inject

class LoadRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepositoryDatabase
) {

    suspend operator fun invoke(): Routine {
        return routineRepository.getRoutine()
    }
}