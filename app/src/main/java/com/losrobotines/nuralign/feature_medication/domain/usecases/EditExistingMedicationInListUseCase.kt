package com.losrobotines.nuralign.feature_medication.domain.usecases

import android.util.Log
import com.losrobotines.nuralign.feature_home.domain.usecases.GetPatientIdUseCase
import javax.inject.Inject

class EditExistingMedicationInListUseCase @Inject constructor(
    private val getMedicationInfoFromDatabaseUseCase: GetMedicationInfoFromDatabaseUseCase,
    private val getPatientIdUseCase: GetPatientIdUseCase
) {
    suspend operator fun invoke(
        medicationName: String,
        medicationGrammage: Int
    ) {
        val medicationList = getMedicationInfoFromDatabaseUseCase(getPatientIdUseCase())
        for (med in medicationList) {
            med!!.let {
                if (it.medicationName == medicationName
                    && it.medicationGrammage == medicationGrammage
                ) {
                    //editar dicha medicación
                } else {
                    Log.d(
                        "EditExistingMedicationInListUseCase",
                        "$medicationName - $medicationGrammage doesn't exists in list"
                    )
                }
            }
        }
    }
}
