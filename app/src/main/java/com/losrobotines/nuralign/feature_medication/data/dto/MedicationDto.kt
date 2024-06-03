package com.losrobotines.nuralign.feature_medication.data.dto

import com.google.gson.annotations.SerializedName

data class MedicationDto(
    @SerializedName("patientId") val patientId: Short,
    @SerializedName("name") val name: String,
    @SerializedName("grammage") val grammage: Int,
    //@SerializedName("days") val days: Short,
    @SerializedName("flag") val flag: String
)