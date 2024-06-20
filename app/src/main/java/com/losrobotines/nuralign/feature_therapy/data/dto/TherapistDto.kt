package com.losrobotines.nuralign.feature_therapy.data.dto

import com.google.gson.annotations.SerializedName

data class TherapistDto(
    @SerializedName("therapistId") val therapistId: Short? = null,
    @SerializedName("therapistFirstName") val therapistFirstName: String,
    @SerializedName("therapistLastName") val therapistLastName: String,
    @SerializedName("therapistEmail") val therapistEmail: String,
    @SerializedName("therapistPhone") val therapistPhone: Int
)