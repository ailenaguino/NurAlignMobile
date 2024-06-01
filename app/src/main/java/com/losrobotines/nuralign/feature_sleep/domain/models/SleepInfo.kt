package com.losrobotines.nuralign.feature_sleep.domain.models

data class SleepInfo(
    val patientId: Short,
    val effectiveDate: String,
    val sleepHours: Short,
    val bedTime: String,
    val negativeThoughtsFlag: String,
    val anxiousFlag: String,
    val sleepStraightFlag: String,
    val sleepNotes: String
)
