package com.losrobotines.nuralign.feature_routine.domain

import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity

data class Routine(
    var id: Int = 0,
    var sleepTime: String,
    var activity: String,
    var activityTime: String,
    var activityDays: List<String>
)

fun Routine.toDatabase(): RoutineEntity {
    return RoutineEntity(
        id = id,
        sleepTime = sleepTime,
        activity = activity,
        activityTime = activityTime,
        activityDays = activityDays
    )
}

fun RoutineEntity.toDomain(): Routine {
    return Routine(
        id = id,
        sleepTime = sleepTime,
        activity = activity,
        activityTime = activityTime,
        activityDays = activityDays
    )
}