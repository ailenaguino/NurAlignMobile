package com.losrobotines.nuralign.feature_medication.domain.usecases.tracker

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationTrackerProvider
import javax.inject.Inject

class SaveMedicationTrackerInfoUseCase @Inject constructor(
    private val medicationTrackerProvider: MedicationTrackerProvider
) {
    suspend operator fun invoke(medicationTrackerInfo: MedicationTrackerInfo): Result<Unit> {
        return try {
            medicationTrackerProvider.saveMedicationTrackerData(medicationTrackerInfo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
