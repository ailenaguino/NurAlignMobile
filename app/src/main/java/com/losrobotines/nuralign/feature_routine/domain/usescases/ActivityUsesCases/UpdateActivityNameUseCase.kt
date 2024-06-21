package com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases

import android.util.Log
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import javax.inject.Inject

class UpdateActivityNameUseCase @Inject constructor(
    private val routineProvider: RoutineProvider
) {
    suspend operator fun invoke(activity: Activity, name: String) {
        val currentRoutine = routineProvider.getRoutine()
        val updatedActivities = currentRoutine.activities.toMutableList()
        val index = updatedActivities.indexOf(activity)
        if (index >= 0) {
            updatedActivities[index] = updatedActivities[index].copy(name = name)
            val updatedRoutine = currentRoutine.copy(activities = updatedActivities)
            routineProvider.addRoutine(updatedRoutine)
        }
    }
}