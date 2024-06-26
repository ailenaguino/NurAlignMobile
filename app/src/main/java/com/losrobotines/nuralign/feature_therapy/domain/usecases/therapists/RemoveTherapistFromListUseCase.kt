package com.losrobotines.nuralign.feature_therapy.domain.usecases.therapists

import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapistProvider
import javax.inject.Inject

class RemoveTherapistFromListUseCase @Inject constructor(
    private val therapistProvider: TherapistProvider
) {
    suspend operator fun invoke(patientId: Short, therapistInfo: TherapistInfo): Result<Unit> {
        return try {
            therapistProvider.deleteTherapistInfo(patientId, therapistInfo.therapistId!!)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
