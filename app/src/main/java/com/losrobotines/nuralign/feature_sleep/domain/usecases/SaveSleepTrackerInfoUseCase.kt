package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class SaveSleepTrackerInfoUseCase @Inject constructor(
    private val sleepTrackerProvider: SleepTrackerProvider
) {
    suspend fun execute(sleepInfo: SleepInfo) {
        sleepTrackerProvider.saveSleepData(sleepInfo)
    }
}