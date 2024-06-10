package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class CheckIfSleepTrackerWasCompletedUseCase @Inject constructor(private val sleepTrackerProvider: SleepTrackerProvider) {

    suspend operator fun invoke(domainId: Int): Boolean{
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val result = sleepTrackerProvider.getTodaysTracker(domainId,date)
        return (result != null)
    }
}