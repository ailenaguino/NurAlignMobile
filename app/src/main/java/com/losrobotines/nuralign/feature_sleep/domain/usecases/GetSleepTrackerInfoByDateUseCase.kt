package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class GetSleepTrackerInfoByDateUseCase @Inject constructor(private val sleepTrackerProvider: SleepTrackerProvider) {

    suspend operator fun invoke(patientId : Int, date: String): SleepInfo?{
        return sleepTrackerProvider.getTodaysTracker(patientId, date)
    }
}