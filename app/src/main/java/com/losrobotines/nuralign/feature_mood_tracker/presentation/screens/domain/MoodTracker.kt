package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain

data class MoodTrackerInfo(
    val patientId: Short,
    val effectiveDate: String,
    val highestValue: String,
    val lowestValue: String,
    val highestNote: String,
    val lowestNote: String,
    val irritableValue: String,
    val irritableNote: String,
    val anxiousValue: String,
    val anxiousNote: String
)


