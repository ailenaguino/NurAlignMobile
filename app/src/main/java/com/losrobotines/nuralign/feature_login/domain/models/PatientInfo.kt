package com.losrobotines.nuralign.feature_login.domain.models

data class PatientInfo(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    var birthDate: String,
    var sex: String,
    val nickname: String
)
