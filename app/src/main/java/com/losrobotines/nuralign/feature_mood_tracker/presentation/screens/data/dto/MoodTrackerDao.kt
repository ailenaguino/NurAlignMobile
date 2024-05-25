package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.dto

import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.short
import org.ktorm.schema.varchar

object MoodTrackerDao : Table<Nothing>("mood_tracker") {
    val patient_id = short("patient_id").primaryKey()//.references(Patient) { it.patient_id }
    val effectiveDate = date("effective_date")
    val highestValue = varchar("highest_value")
    val lowestValue = varchar("lowest_value")
    val highestNote = varchar("highest_notes")
    val lowestNote = varchar("lowest_notes")
    val irritableValue = varchar("irritable_value")
    val irritableNote = varchar("irritable_notes")
    val anxiousValue = varchar("anxious_value")
    val anxiousNote = varchar("anxious_notes")


}