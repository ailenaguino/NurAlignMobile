package com.losrobotines.nuralign.feature_routine.data

import android.util.Log
import com.losrobotines.nuralign.feature_routine.data.database.RoutineDao
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import com.losrobotines.nuralign.feature_routine.domain.models.Routine
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.models.toDatabase
import com.losrobotines.nuralign.feature_routine.domain.models.toDomain
import javax.inject.Inject

class RoutineProviderImpl @Inject constructor(private val dao: RoutineDao) : RoutineProvider {

    override suspend fun addRoutine(routine: Routine) {
        dao.insert(routine.toDatabase())
    }

    override suspend fun getRoutine(): Routine {
        val response: List<RoutineEntity> = dao.getAll()
        val routine = if (response.isNotEmpty()) {
            response[0].toDomain()
        } else {
            Routine(
                id = 0,
                sleepTime = "",
                activities = emptyList()
            )
        }
        return routine
    }
}