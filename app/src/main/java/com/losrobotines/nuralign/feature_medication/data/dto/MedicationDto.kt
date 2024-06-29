package com.losrobotines.nuralign.feature_medication.data.dto

import com.google.gson.annotations.SerializedName

data class MedicationDto(
    @SerializedName("id") val patientMedicationId: Short? = null,
    @SerializedName("patientId") val patientId: Short,
    @SerializedName("name") val name: String,
    @SerializedName("grammage") val grammage: Int,
    @SerializedName("flag") val flag: String,
    @SerializedName("enabledFlag") val enabledFlag: String? = "Y"
)