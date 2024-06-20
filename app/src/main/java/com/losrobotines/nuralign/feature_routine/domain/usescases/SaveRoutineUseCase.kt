package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.data.RoutineRepositoryDatabase
import com.losrobotines.nuralign.feature_routine.domain.Routine
import javax.inject.Inject

class SaveRoutineUseCase @Inject constructor(private val routineRepository: RoutineRepositoryDatabase) {
    suspend operator fun invoke(
        bedTimeRoutine: String,
        activity: String,
        activityRoutineTime: String,
        selectedDays: List<String>,
        activity2: String,
        activityTime2: String,
        activityDays2: List<String>,
    ) {
        routineRepository.addRoutine(
            Routine(
                0,
                bedTimeRoutine,
                activity,
                activityRoutineTime,
                selectedDays,
                activity2,
                activityTime2,
                activityDays2
            )
        )
    }
}