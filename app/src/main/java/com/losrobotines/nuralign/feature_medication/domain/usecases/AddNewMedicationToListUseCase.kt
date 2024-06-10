package com.losrobotines.nuralign.feature_medication.domain.usecases

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import javax.inject.Inject

class AddNewMedicationToListUseCase @Inject constructor() {
    operator fun invoke(
        medicationInfo: MedicationInfo,
        medicationList: List<MedicationInfo?>
    ): Result<List<MedicationInfo?>> {
        return try {
            if (medicationList.any {
                    it?.medicationName == medicationInfo.medicationName
                            && it.medicationGrammage == medicationInfo.medicationGrammage
                }) {
                return Result.failure(Exception("Medication already exists"))
            } else {
                val updatedList = medicationList.toMutableList().apply {
                    add(medicationInfo)
                }.toList()
                Result.success(updatedList)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
