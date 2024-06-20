package com.losrobotines.nuralign.feature_therapy.domain.models

data class TherapistInfo(
    val therapistId: Short? = null,
    var therapistFirstName: String,
    var therapistLastName: String,
    var therapistEmail: String,
    var therapistPhone: Int
)