package com.losrobotines.nuralign.feature_home.domain.usecases

import com.losrobotines.nuralign.feature_routine.data.database.RoutineDatabase
import javax.inject.Inject

class ResetDatabaseUseCase @Inject constructor(
    private val database: RoutineDatabase
) {
    operator fun invoke() {
        database.clearAllTables()
    }
}