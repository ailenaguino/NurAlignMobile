package com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases


import android.util.Log
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import javax.inject.Inject

class RemoveActivityUseCase @Inject constructor(
    private val routineProvider: RoutineProvider
) {
    suspend operator fun invoke(activity: Activity) {
        val currentRoutine = routineProvider.getRoutine()
        val updatedActivities = currentRoutine.activities.toMutableList().apply { remove(activity) }
        val updatedRoutine = currentRoutine.copy(activities = updatedActivities)
        routineProvider.addRoutine(updatedRoutine)
    }
}