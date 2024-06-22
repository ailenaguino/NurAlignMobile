package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.domain.models.Routine
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import javax.inject.Inject

class SaveRoutineUseCase @Inject constructor(private val routineProvider: RoutineProvider) {
    suspend operator fun invoke(
        bedTimeRoutine: String,
        activities: List<Activity>
    ) {
        routineProvider.addRoutine(
            Routine(
                id = 0,
                sleepTime = bedTimeRoutine,
                activities = activities
            )
        )
    }
}