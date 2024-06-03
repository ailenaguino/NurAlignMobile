package com.losrobotines.nuralign.feature_medication.domain.models

data class MedicationInfo(
    val patientId: Short,
    val medicationName: String,
    val medicationGrammage: Int,
    //val medicationDays: Short,
    val medicationOptionalFlag: String,
)