package com.losrobotines.nuralign.feature_medication.domain.usecases.medication

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import javax.inject.Inject

class EditExistingMedicationInListUseCase @Inject constructor(
    private val medicationProvider: MedicationProvider
) {
    suspend operator fun invoke(
        newMedicationName: String,
        newMedicationGrammage: Int,
        newMedicationFlag: String,
        oldMedicationInfo: MedicationInfo,
        medicationList: List<MedicationInfo?>
    ): Result<Unit> {
        return try {
            val updatedMedication = medicationList.find {
                it == oldMedicationInfo
            }?.copy(
                medicationName = newMedicationName.ifBlank { oldMedicationInfo.medicationName },
                medicationGrammage = if (newMedicationGrammage != 0) {
                    newMedicationGrammage
                } else {
                    oldMedicationInfo.medicationGrammage
                },
                medicationOptionalFlag = newMedicationFlag.ifEmpty {
                    oldMedicationInfo.medicationOptionalFlag
                }
            )

            if (updatedMedication != null) {
                //medicationProvider.updateMedicationInfo(updatedMedication)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Medication not found in list"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
