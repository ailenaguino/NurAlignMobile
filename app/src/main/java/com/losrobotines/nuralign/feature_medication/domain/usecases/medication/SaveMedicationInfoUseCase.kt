package com.losrobotines.nuralign.feature_medication.domain.usecases.medication

import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import javax.inject.Inject

class SaveMedicationInfoUseCase @Inject constructor(
    private val userService: UserService
) {
    suspend operator fun invoke(medicationInfo: MedicationInfo): Result<Unit> {
        val preparedInfo = checkForDuplicatedMedication(medicationInfo)
        return if (preparedInfo != null) {
            val saveSuccessful = userService.saveMedicationInfo(preparedInfo)
            if (saveSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to save medication info"))
            }
        } else {
            Result.failure(Exception("Duplicated medication"))
        }
    }

    private suspend fun checkForDuplicatedMedication(medicationInfo: MedicationInfo): MedicationInfo? {
        val medicationList = userService.getMedicationList(userService.getPatientId())

        return if (!isDuplicated(medicationInfo, medicationList)) {
            medicationInfo
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
