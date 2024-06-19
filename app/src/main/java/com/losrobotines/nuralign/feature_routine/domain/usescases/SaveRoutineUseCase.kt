package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.data.RoutineProviderImpl
import com.losrobotines.nuralign.feature_routine.domain.Routine
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import javax.inject.Inject

class SaveRoutineUseCase @Inject constructor(private val routineProvider: RoutineProvider) {
    suspend operator fun invoke(
        bedTimeRoutine: String,
        activity: String,
        activityRoutineTime: String,
        selectedDays: List<String>,
        activity2: String,
        activityTime2: String,
        activityDays2: List<String>,
    ) {
        routineProvider.addRoutine(
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