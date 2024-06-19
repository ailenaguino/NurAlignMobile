package com.losrobotines.nuralign.feature_routine.data

import com.losrobotines.nuralign.feature_routine.data.database.RoutineDao
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import com.losrobotines.nuralign.feature_routine.domain.Routine
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.toDatabase
import com.losrobotines.nuralign.feature_routine.domain.toDomain
import javax.inject.Inject

class RoutineProviderImpl @Inject constructor(private val dao: RoutineDao): RoutineProvider {

    override suspend fun addRoutine(routine: Routine) {
        dao.insert(routine.toDatabase())
    }
    override suspend fun getRoutine(): Routine {
        val response: List<RoutineEntity> = dao.getAll()
        return if (response.isNotEmpty()) {
            response[0].toDomain()
        } else {
            RoutineEntity(
                id = 0,
                sleepTime = "",
                activity = "",
                activityTime = "",
                activityDays = emptyList(),
                activity2 = "",
                activityTime2 = "",
                activityDays2 = emptyList()
            ).toDomain()
        }
    }
}