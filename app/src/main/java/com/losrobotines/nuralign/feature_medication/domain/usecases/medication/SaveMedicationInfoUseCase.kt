package com.losrobotines.nuralign.feature_medication.domain.usecases.medication

import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import javax.inject.Inject

class SaveMedicationInfoUseCase @Inject constructor(
    private val userService: UserService,
    private val medicationProvider: MedicationProvider
) {
    suspend operator fun invoke(medicationInfo: MedicationInfo): Result<Unit> {
        val preparedInfo = checkForDuplicatedMedication(medicationInfo)

        return if (preparedInfo != null) {
            try {
                val saveSuccessful = medicationProvider.saveMedicationInfo(preparedInfo)
                if (saveSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Failed to save medication info"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Duplicated medication"))
            }
        } else {
            Result.failure(Exception("Failed to save medication info"))
        }
    }

    private suspend fun checkForDuplicatedMedication(medicationInfo: MedicationInfo): MedicationInfo? {
        val patientId = userService.getPatientId().getOrNull() ?: return null
        val result =
            userService.getMedicationList(patientId)

        return if (result.isSuccess) {
            val medicationList = result.getOrNull()
            if (medicationList != null && !isDuplicated(medicationInfo, medicationList)) {
                medicationInfo
            } else {
                null
            }
        } else {
            null
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
