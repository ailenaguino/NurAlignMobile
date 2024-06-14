package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.data.RoutineRepositoryDatabase
import com.losrobotines.nuralign.feature_routine.domain.Routine
import javax.inject.Inject

class SaveRoutineUseCase @Inject constructor(private val routineRepository: RoutineRepositoryDatabase) {
    suspend operator fun invoke(
        bedTimeRoutine: kotlin.String,
        activity: kotlin.String,
        activityRoutineTime: kotlin.String,
        selectedDays: List<String>
    ) {
        routineRepository.addRoutine(
            Routine(
                0,
                bedTimeRoutine,
                activity,
                activityRoutineTime,
                selectedDays
            )
        )
    }
}