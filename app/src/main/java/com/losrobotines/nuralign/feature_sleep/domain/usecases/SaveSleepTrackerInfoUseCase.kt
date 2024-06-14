package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class SaveSleepTrackerInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val formatTimeUseCase: FormatTimeUseCase,
    private val sleepTrackerProvider: SleepTrackerProvider
) {
    suspend operator fun invoke(
        patientId: Short,
        currentDate: String,
        sleepHours: Int,
        bedTime: String,
        negativeThoughts: Boolean,
        anxiousBeforeSleep: Boolean,
        sleptThroughNight: Boolean,
        additionalNotes: String
    ) {
        val formattedBedTime = bedTime.let { formatTimeUseCase.removeColonFromTime(it) }
        val sleepInfo = SleepInfo(
            patientId,
            currentDate,
            sleepHours.toShort(),
            formattedBedTime ?: "",
            if (negativeThoughts) "Y" else "N",
            if (anxiousBeforeSleep) "Y" else "N",
            if (sleptThroughNight) "Y" else "N",
            additionalNotes ?: ""
        )
        sleepTrackerProvider.saveSleepData(sleepInfo)
    }

}
