package com.losrobotines.nuralign.feature_routine.domain.models

import androidx.room.TypeConverter
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity

data class Routine(
    var id: Int = 0,
    var sleepTime: String,
    var activities: List<Activity>
)

data class Activity(
    var name: String,
    var time: String,
    var days: List<String>
)

fun RoutineEntity.toDomain(): Routine {
    return Routine(
        id = id,
        sleepTime = sleepTime,
        activities = activities
    )
}

fun Routine.toDatabase(): RoutineEntity {
    return RoutineEntity(
        id = id,
        sleepTime = sleepTime,
        activities = activities
    )
}

