package com.losrobotines.nuralign.feature_medication.domain.usecases

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import javax.inject.Inject

class RemoveMedicationFromListUseCase @Inject constructor() {

    operator fun invoke(
        medicationInfo: MedicationInfo,
        medicationList: List<MedicationInfo?>
    ): Result<List<MedicationInfo?>> {
        return try {
            val updatedList = medicationList.toMutableList().also {
                it.remove(medicationInfo)
            }
            Result.success(updatedList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
