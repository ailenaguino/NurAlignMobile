package com.losrobotines.nuralign.feature_medication.domain.usecases.medication

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import javax.inject.Inject

class RemoveMedicationFromListUseCase @Inject constructor(
    private val medicationProvider: MedicationProvider
) {
    suspend operator fun invoke(
        medicationInfo: MedicationInfo
    ): Result<Unit> {
        return try {
            medicationProvider.deleteMedicationInfo(medicationInfo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
