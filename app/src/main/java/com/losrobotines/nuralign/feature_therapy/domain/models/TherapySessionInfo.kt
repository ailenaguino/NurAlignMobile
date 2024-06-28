package com.losrobotines.nuralign.feature_therapy.domain.models

data class TherapySessionInfo (
    val id: Short? = null,
    val patientId: Short,
    val therapistId: Short,
    val sessionDate: String,
    val sessionTime: Short,
    val preSessionNotes: String?,
    val postSessionNotes: String?,
    val sessionFeel: String?
)