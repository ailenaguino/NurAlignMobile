package com.losrobotines.nuralign.feature_therapy.domain.usecases.therapists

import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapistProvider
import javax.inject.Inject

class SaveTherapistInfoUseCase @Inject constructor(
    private val therapistProvider: TherapistProvider
) {

    suspend operator fun invoke(therapistInfo: TherapistInfo): Result<Unit> {
        return try {
            therapistProvider.saveTherapistInfo(therapistInfo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
