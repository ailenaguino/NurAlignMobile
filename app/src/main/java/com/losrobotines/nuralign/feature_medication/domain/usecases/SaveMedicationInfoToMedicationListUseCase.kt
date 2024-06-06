package com.losrobotines.nuralign.feature_medication.domain.usecases

import android.util.Log
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import javax.inject.Inject

class SaveMedicationInfoToMedicationListUseCase @Inject constructor() {
    operator fun invoke(
        medicationInfo: MedicationInfo,
        medicationList: MutableList<MedicationInfo?>
    ): MutableList<MedicationInfo?> {
        if (medicationInfo !in medicationList) {
            medicationList.add(medicationInfo)
        } else {
            Log.d(
                "SaveMedicationInfoToMedicationListUseCase",
                "medication ${medicationInfo.medicationName} - ${medicationInfo.medicationGrammage} already exists in the list"
            )
        }
        return medicationList
    }
}
