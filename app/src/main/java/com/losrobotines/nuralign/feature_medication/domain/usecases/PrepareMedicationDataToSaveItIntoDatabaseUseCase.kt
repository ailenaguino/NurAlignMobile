package com.losrobotines.nuralign.feature_medication.domain.usecases

import com.losrobotines.nuralign.feature_home.domain.usecases.GetPatientIdUseCase
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import javax.inject.Inject

class PrepareMedicationDataToSaveItIntoDatabaseUseCase @Inject constructor(
    private val getMedicationInfoFromDatabaseUseCase: GetMedicationInfoFromDatabaseUseCase,
    private val getPatientIdUseCase: GetPatientIdUseCase
) {
    private lateinit var oldMedicationList: MutableList<MedicationInfo?>
    private lateinit var newMedicationList: MutableList<MedicationInfo>

    suspend operator fun invoke(medicationList: MutableList<MedicationInfo>): MutableList<MedicationInfo> {
        oldMedicationList = getMedicationInfoFromDatabaseUseCase(getPatientIdUseCase())
        newMedicationList = medicationList

        oldMedicationList.let {
            for (oldMed in oldMedicationList) {
                for (newMed in newMedicationList) {
                    if (newMed.medicationName == oldMed!!.medicationName
                        && newMed.medicationGrammage == oldMed.medicationGrammage) {
                        newMedicationList.remove(newMed)
                    }
                }
            }
        }
        return newMedicationList
    }
}
