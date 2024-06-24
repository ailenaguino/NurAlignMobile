package com.losrobotines.nuralign.feature_therapy.domain.models

data class TherapistInfo(
    val id: Short? = null,
    var name: String,
    var lastName: String,
    var email: String,
    var phoneNumber: Int,
    var registeredFlag: String = "N"
)