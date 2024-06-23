package com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases

import android.util.Log
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import javax.inject.Inject

class RemoveSelectedDayFromActivityUseCase @Inject constructor(
    private val routineProvider: RoutineProvider
) {
    suspend operator fun invoke(activity: Activity, day: String) {
        val currentRoutine = routineProvider.getRoutine()
        val updatedActivities = currentRoutine.activities.toMutableList()
        val index = updatedActivities.indexOf(activity)
        if (index >= 0) {
            val updatedDays = updatedActivities[index].days.toMutableList().apply { remove(day) }
            updatedActivities[index] = updatedActivities[index].copy(days = updatedDays)
            val updatedRoutine = currentRoutine.copy(activities = updatedActivities)
            routineProvider.addRoutine(updatedRoutine)
        }
    }
}