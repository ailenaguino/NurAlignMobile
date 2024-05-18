package com.losrobotines.nuralign.feature_sleep.data.models

import com.google.gson.annotations.SerializedName

data class SleepTrackerDto(
    @SerializedName("patientId") val patientId: Short,
    @SerializedName("sleepHours") val sleepHours: Short,
    @SerializedName("bedTime") val bedTime: Short,
    @SerializedName("negativeThoughtsFlag") val negativeThoughtsFlag: String,
    @SerializedName("anxiousFlag") val anxiousFlag: String,
    @SerializedName("sleepStraightFlag") val sleepStraightFlag: String,
    @SerializedName("sleepNotes") val sleepNotes: String,
    @SerializedName("effectiveDate") val effectiveDate: String
)
