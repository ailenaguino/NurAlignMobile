package com.losrobotines.nuralign.feature_medication.domain.usecases.tracker

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationTrackerProvider
import javax.inject.Inject

class UpdateMedicationTrackerInfoUseCase @Inject constructor(
    private val medicationTrackerProvider: MedicationTrackerProvider
) {
    suspend operator fun invoke(medicationTrackerInfo: MedicationTrackerInfo): Result<MedicationTrackerInfo> {
        return try {
            medicationTrackerProvider.updateMedicationTrackerData(medicationTrackerInfo)
            Result.success(medicationTrackerInfo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

