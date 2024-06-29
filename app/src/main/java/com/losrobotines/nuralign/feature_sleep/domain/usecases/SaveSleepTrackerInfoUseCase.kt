package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class SaveSleepTrackerInfoUseCase @Inject constructor(
    private val formatTimeUseCase: FormatTimeUseCase,
    private val sleepTrackerProvider: SleepTrackerProvider
) {
    suspend operator fun invoke(
        patientId: Short,
        currentDate: String,
        sleepHours: Short,
        bedTime: String,
        negativeThoughts: Boolean,
        anxiousBeforeSleep: Boolean,
        sleptThroughNight: Boolean,
        additionalNotes: String
    ) : Result<Unit> {
        val formattedBedTime = bedTime.let { formatTimeUseCase.removeColonFromTime(it) }
        return try {
            val sleepInfo = SleepInfo(
                patientId,
                currentDate,
                sleepHours,
                formattedBedTime,
                if (negativeThoughts) "Y" else "N",
                if (anxiousBeforeSleep) "Y" else "N",
                if (sleptThroughNight) "Y" else "N",
                additionalNotes
            )
            sleepTrackerProvider.saveSleepData(sleepInfo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
