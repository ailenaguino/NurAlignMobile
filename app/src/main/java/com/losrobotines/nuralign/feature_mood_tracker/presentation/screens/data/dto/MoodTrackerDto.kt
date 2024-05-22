package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.dto

import com.google.gson.annotations.SerializedName

data class MoodTrackerDto(
    @SerializedName("patientId") val patientId: Short,
    @SerializedName("effectiveDate") val effectiveDate: String,
    @SerializedName("highestValue") val highestValue: String,
    @SerializedName("highestNotes") val highestNotes: String?,
    @SerializedName("lowestValue") val lowestValue: String,
    @SerializedName("lowestNotes") val lowestNotes: String?,
    @SerializedName("irritableValue") val irritableValue: String,
    @SerializedName("irritableNotes") val irritableNotes: String?,
    @SerializedName("anxiousValue") val anxiousValue: String,
    @SerializedName("anxiousNotes") val anxiousNotes: String?
)