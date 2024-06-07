package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.data.RoutineRepositoryDatabase
import com.losrobotines.nuralign.feature_routine.domain.Routine
import javax.inject.Inject

class SaveRoutineUseCase @Inject constructor(private val routineRepository: RoutineRepositoryDatabase) {
    suspend operator fun invoke(
        routine: Routine
    ) {
        routineRepository.addRoutine(routine)
    }
}


