package com.losrobotines.nuralign.feature_therapy.data.dto

import com.google.gson.annotations.SerializedName

data class TherapistDto(
    @SerializedName("id") val id: Short? = null,
    @SerializedName("name") val name: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("phoneNumber") val phoneNumber: Int,
    @SerializedName("registeredFlag") val registeredFlag: String
)