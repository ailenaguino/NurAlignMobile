package com.losrobotines.nuralign.feature_sleep.data.models

import org.ktorm.schema.*

object SleepTrackerDao : Table<Nothing>("sleep_tracker") {
    val patient_id = short("patient_id").primaryKey()//.references(Patient) { it.patient_id }
    val effective_date = varchar("effective_date").primaryKey()
    val sleep_hours = short("sleep_hours")
    val bed_time = short("bed_time")
    val negative_thoughts_flag = varchar("negative_thoughts_flag")
    val anxious_flag = varchar("anxious_flag")
    val sleep_straight_flag = varchar("sleep_straight_flag")
    val sleep_notes = varchar("sleep_notes")

}