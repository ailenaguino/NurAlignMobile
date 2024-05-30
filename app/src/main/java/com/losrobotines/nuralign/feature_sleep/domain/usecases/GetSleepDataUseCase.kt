package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class GetSleepDataUseCase @Inject constructor(
    private val sleepRepository: SleepRepository
) {
    suspend fun execute(patientId: Int): SleepInfo? {
        return sleepRepository.getSleepData(patientId)
    }
}