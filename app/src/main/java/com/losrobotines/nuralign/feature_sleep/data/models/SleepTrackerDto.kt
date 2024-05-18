package com.losrobotines.nuralign.feature_sleep.data.models

data class SleepTrackerDto(
    val patientId: Short,
    val effectiveDate: String,
    val sleepHours: Short,
    val bedTime: Short,
    val negativeThoughtsFlag: String,
    val anxiousFlag: String,
    val sleepStraightFlag: String,
    val sleepNotes: String
)
