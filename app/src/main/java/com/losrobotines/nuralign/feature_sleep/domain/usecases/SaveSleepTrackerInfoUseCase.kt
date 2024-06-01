package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class SaveSleepTrackerInfoUseCase @Inject constructor(
    private val sleepRepository: SleepRepository
) {
    suspend fun execute(sleepInfo: SleepInfo) {
        sleepRepository.saveSleepData(sleepInfo)
    }
}