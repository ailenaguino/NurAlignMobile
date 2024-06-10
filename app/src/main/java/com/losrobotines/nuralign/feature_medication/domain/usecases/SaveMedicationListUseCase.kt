package com.losrobotines.nuralign.feature_medication.domain.usecases

import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import javax.inject.Inject

class SaveMedicationListUseCase @Inject constructor(
    private val userService: UserService
) {
    private lateinit var oldMedicationList: List<MedicationInfo?>
    private lateinit var newMedicationList: List<MedicationInfo?>
    suspend operator fun invoke(medicationList: List<MedicationInfo?>): Boolean {
        return userService.saveMedicationList(prepareMedicationList(medicationList))
    }

    private suspend fun prepareMedicationList(medicationList: List<MedicationInfo?>): List<MedicationInfo?> {
        oldMedicationList = userService.getMedicationList(userService.getPatientId())
        newMedicationList = medicationList

        newMedicationList.toMutableList().removeIf {
            it in oldMedicationList
        }

        return oldMedicationList.toMutableList().apply {
            addAll(newMedicationList)
        }
    }
}
