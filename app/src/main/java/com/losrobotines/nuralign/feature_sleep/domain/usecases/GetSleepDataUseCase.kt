package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class GetSleepDataUseCase @Inject constructor(
    private val sleepTrackerProvider: SleepTrackerProvider
) {
    suspend fun execute(patientId: Int): SleepInfo? {
        return sleepTrackerProvider.getSleepData(patientId)
    }
}