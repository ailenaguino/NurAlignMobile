package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class UpdateSleepTrackerUseCase @Inject constructor(
    private val formatTimeUseCase: FormatTimeUseCase,
    private val provider: SleepTrackerProvider
) {
    suspend operator fun invoke(
        patientId: Short,
        currentDate: String,
        sleepHours: Short,
        bedTime: String,
        negativeThoughts: Boolean,
        anxiousBeforeSleep: Boolean,
        sleptThroughNight: Boolean,
        additionalNotes: String) : Result<Unit>  {
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
            provider.updateSleepData(sleepInfo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}