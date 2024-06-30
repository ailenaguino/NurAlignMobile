package com.losrobotines.nuralign.feature_medication.domain.usecases.medication

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import javax.inject.Inject

class SaveMedicationInfoUseCase @Inject constructor(
    private val medicationProvider: MedicationProvider
) {
    suspend operator fun invoke(medicationInfo: MedicationInfo): Result<Unit> {
        val preparedInfo =
            checkForDuplicatedMedication(medicationInfo)?.copy(medicationOptionalFlag = medicationInfo.medicationOptionalFlag.ifEmpty { "N" })

        return try {
            val result = medicationProvider.saveMedicationInfo(preparedInfo)
            if (result) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to save medication info"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Failed to save medication info"))
        }
    }

    private suspend fun checkForDuplicatedMedication(medicationInfo: MedicationInfo): MedicationInfo? {
        val result =
            medicationProvider.getMedicationList(medicationInfo.patientId)

        return if (result.isEmpty()) {
            if (!isDuplicated(medicationInfo, result)) {
                medicationInfo
            } else {
                null
            }
        } else {
            medicationInfo
        }
    }

    private fun isDuplicated(
        medicationInfo: MedicationInfo,
        medicationList: List<MedicationInfo?>
    ): Boolean {
        return medicationList.any { existingMedication ->
            existingMedication?.let {
                it.medicationName == medicationInfo.medicationName
                        && it.medicationGrammage == medicationInfo.medicationGrammage
            } ?: false
        }
    }
}
