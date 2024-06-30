package com.losrobotines.nuralign.feature_medication.domain.models

data class MedicationTrackerInfo (
    val patientMedicationId: Short,
    val effectiveDate: String,
    var takenFlag: String
)