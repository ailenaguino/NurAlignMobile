package com.losrobotines.nuralign.feature_medication.data.dto

import com.google.gson.annotations.SerializedName

data class MedicationTrackerDto(
    @SerializedName("patientMedicationId") val patientMedicationId: Short,
    @SerializedName("effectiveDate") val effectiveDate: String,
    @SerializedName("takenFlag") val takenFlag: String
)