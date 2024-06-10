package com.losrobotines.nuralign.feature_medication.domain.usecases

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import javax.inject.Inject

class EditExistingMedicationInListUseCase @Inject constructor() {
    operator fun invoke(
        newMedicationName: String,
        newMedicationGrammage: Int,
        newMedicationFlag: String,
        oldMedicationInfo: MedicationInfo,
        medicationList: List<MedicationInfo?>
    ): Result<List<MedicationInfo?>> {
        return try {
            val updatedList = medicationList.toMutableList().apply {
                val index = indexOf(oldMedicationInfo)
                if (index != -1) {
                    this[index] = this[index]?.copy(
                        medicationName = newMedicationName.ifBlank { oldMedicationInfo.medicationName },
                        medicationGrammage = if (newMedicationGrammage != 0) { newMedicationGrammage }
                            else { oldMedicationInfo.medicationGrammage },
                        medicationOptionalFlag = if (newMedicationFlag.isNotEmpty()){
                            newMedicationFlag
                        } else {
                            this[index]?.medicationOptionalFlag ?: oldMedicationInfo.medicationOptionalFlag
                        }
                    )
                } else {
                    return Result.failure(Exception("Medication not found in list"))
                }
            }
            Result.success(updatedList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
