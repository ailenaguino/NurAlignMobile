package com.losrobotines.nuralign.feature_medication.domain.models

data class MedicationInfo(
    val patientMedicationId: Short? = null,
    val patientId: Short,
    var medicationName: String,
    var medicationGrammage: Int,
    //val medicationDays: Short,
    var medicationOptionalFlag: String,
)