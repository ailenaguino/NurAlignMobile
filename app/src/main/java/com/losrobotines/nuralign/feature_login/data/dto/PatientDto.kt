package com.losrobotines.nuralign.feature_login.data.dto

import com.google.gson.annotations.SerializedName

data class PatientDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("birthdate") val birtDate: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("nickname") val nickname: String
)
