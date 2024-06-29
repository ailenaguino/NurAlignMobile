package com.losrobotines.nuralign.feature_therapy.domain.usecases.therapy_session

import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapySessionProvider
import javax.inject.Inject

class EditTherapySessionUseCase @Inject constructor(
    private val therapySessionProvider: TherapySessionProvider
) {
    suspend operator fun invoke(therapySessionInfo: TherapySessionInfo): Result<Unit> {
        return try {
            therapySessionProvider.updateTherapySessionInfo(therapySessionInfo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}