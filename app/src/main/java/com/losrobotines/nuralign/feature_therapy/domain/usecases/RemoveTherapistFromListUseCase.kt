package com.losrobotines.nuralign.feature_therapy.domain.usecases

import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapistProvider
import javax.inject.Inject

class RemoveTherapistFromListUseCase @Inject constructor(
    private val therapistProvider: TherapistProvider
) {
    suspend operator fun invoke(patientId: Short, therapistInfo: TherapistInfo): Result<Unit> {
        return try {
            therapistProvider.deleteTherapistInfo(patientId, therapistInfo.id!!)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
