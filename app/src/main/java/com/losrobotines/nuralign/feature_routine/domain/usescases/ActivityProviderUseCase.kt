package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.AddActivityUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.AddSelectedDayToActivityUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.RemoveActivityUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.RemoveSelectedDayFromActivityUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.UpdateActivityNameUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.UpdateActivityTimeUseCase
import javax.inject.Inject

class ActivityProviderUseCase @Inject constructor(
    private val addActivityUseCase: AddActivityUseCase,
    private val removeActivityUseCase: RemoveActivityUseCase,
    private val updateActivityNameUseCase: UpdateActivityNameUseCase,
    private val updateActivityTimeUseCase: UpdateActivityTimeUseCase,
    private val addSelectedDayToActivityUseCase: AddSelectedDayToActivityUseCase,
    private val removeSelectedDayFromActivityUseCase: RemoveSelectedDayFromActivityUseCase
) {
    suspend fun addActivity(activity: Activity) {
        addActivityUseCase.invoke(activity)
    }

    suspend fun removeActivity(activity: Activity) {
        removeActivityUseCase.invoke(activity)
    }

    suspend fun updateActivityName(activity: Activity, name: String) {
        updateActivityNameUseCase.invoke(activity, name)
    }

    suspend fun updateActivityTime(activity: Activity, time: String) {
        updateActivityTimeUseCase.invoke(activity, time)
    }

    suspend fun addSelectedDayToActivity(activity: Activity, day: String) {
        addSelectedDayToActivityUseCase.invoke(activity, day)
    }

    suspend fun removeSelectedDayFromActivity(activity: Activity, day: String) {
        removeSelectedDayFromActivityUseCase.invoke(activity, day)
    }
}