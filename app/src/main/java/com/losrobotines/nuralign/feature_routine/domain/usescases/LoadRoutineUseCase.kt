package com.losrobotines.nuralign.feature_routine.domain.usescases


import com.losrobotines.nuralign.feature_routine.domain.models.Routine
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import javax.inject.Inject

class LoadRoutineUseCase @Inject constructor(
    private val routineProvider: RoutineProvider
) {

    suspend operator fun invoke(): Routine {
        return routineProvider.getRoutine()
    }
}