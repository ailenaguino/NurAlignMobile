package com.losrobotines.nuralign.feature_medication.domain.usecases

import android.util.Log
import com.losrobotines.nuralign.feature_home.domain.usecases.GetPatientIdUseCase
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import javax.inject.Inject

class EditExistingMedicationInListUseCase @Inject constructor(
    private val getMedicationInfoFromDatabaseUseCase: GetMedicationInfoFromDatabaseUseCase,
    private val getPatientIdUseCase: GetPatientIdUseCase
) {
    suspend operator fun invoke(
        newMedicationName: String,
        newMedicationGrammage: Int,
        newMedicationFlag: String,
        oldMedicationInfo: MedicationInfo
    ): MedicationInfo? {
        val medicationList = getMedicationInfoFromDatabaseUseCase(getPatientIdUseCase())

        if (oldMedicationInfo in medicationList) {
            oldMedicationInfo.medicationName = newMedicationName
            oldMedicationInfo.medicationGrammage = newMedicationGrammage
            oldMedicationInfo.medicationOptionalFlag = newMedicationFlag

            return oldMedicationInfo
        } else {
            Log.d(
                "EditExistingMedicationInListUseCase",
                "${oldMedicationInfo.medicationName} - ${oldMedicationInfo.medicationGrammage} doesn't exists in list"
            )
            return null
        }
    }
}
