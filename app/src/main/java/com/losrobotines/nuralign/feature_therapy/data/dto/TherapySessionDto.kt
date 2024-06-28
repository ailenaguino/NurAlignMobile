package com.losrobotines.nuralign.feature_therapy.data.dto

import com.google.gson.annotations.SerializedName

data class TherapySessionDto(
    @SerializedName("id") val therapySessionId: Short? = null,
    @SerializedName("patientId") val patientId: Short,
    @SerializedName("therapistId") val therapistId: Short,
    @SerializedName("effectiveDate") val effectiveDate: String,
    @SerializedName("sessionTime") val sessionTime: Short,
    @SerializedName("preSessionNotes") val preSessionNotes: String?,
    @SerializedName("postSessionNotes") val postSessionNotes: String?,
    @SerializedName("sessionFeel") val sessionFeel: String?
)