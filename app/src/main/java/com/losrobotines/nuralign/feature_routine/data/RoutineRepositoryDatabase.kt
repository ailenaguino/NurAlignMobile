package com.losrobotines.nuralign.feature_routine.data

import com.losrobotines.nuralign.feature_routine.data.database.RoutineDao
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import javax.inject.Inject

class RoutineRepositoryDatabase @Inject constructor(private val dao: RoutineDao) {

    fun addRoutine(routine: RoutineEntity) {
        dao.insert(routine)
    }

    fun getRoutines(): List<RoutineEntity> {
        return dao.getAll()
    }

    fun getRoutine(): RoutineEntity {
        val response: List<RoutineEntity> = dao.getAll()
        return if (response.isNotEmpty()) {
            response[0]
        } else {

            RoutineEntity(
                id = 0,
                sleepTime = "",
                activity = "",
                activityTime = ""
            )
        }
    }
}