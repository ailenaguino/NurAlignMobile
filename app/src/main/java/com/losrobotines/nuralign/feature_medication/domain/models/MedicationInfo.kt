package com.losrobotines.nuralign.feature_medication.domain.models

data class MedicationInfo(
    val patientMedicationId: Short? = null,
    val patientId: Short,
    var medicationName: String,
    var medicationGrammage: Int,
    var medicationOptionalFlag: String = "N",
)