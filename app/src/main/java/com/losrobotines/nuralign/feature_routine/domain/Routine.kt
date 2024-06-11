package com.losrobotines.nuralign.feature_routine.domain

import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity

data class Routine(
    var id: Int = 0,
    var sleepTime: String,
    var activity: String,
    var activityTime: String,
    var activityDays: List<String>,
    var activity2: String,
    var activityTime2: String,
    var activityDays2: List<String>,
)

fun Routine.toDatabase(): RoutineEntity {
    return RoutineEntity(
        id = id,
        sleepTime = sleepTime,
        activity = activity,
        activityTime = activityTime,
        activityDays = activityDays,
        activity2 = activity2,
        activityTime2 = activityTime2,
        activityDays2 = activityDays2
    )
}

fun RoutineEntity.toDomain(): Routine {
    return Routine(
        id = id,
        sleepTime = sleepTime,
        activity = activity,
        activityTime = activityTime,
        activityDays = activityDays,
        activity2 = activity2,
        activityTime2 = activityTime2,
        activityDays2 = activityDays2
    )
}