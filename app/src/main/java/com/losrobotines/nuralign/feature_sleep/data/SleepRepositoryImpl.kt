package com.losrobotines.nuralign.feature_sleep.data

import com.losrobotines.nuralign.feature_sleep.data.models.SleepTracker
import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import org.ktorm.database.Database
import org.ktorm.dsl.insert

class SleepRepositoryImpl: SleepRepository {

    private val database = Database.connect("jdbc:mysql://localhost:3306/ktorm", user = "root", password = "***")

    override suspend fun saveSleepData(sleepInfo: SleepInfo) {

        database.insert(SleepTracker) {
            set(it.patient_id, sleepInfo.patientId)
            set(it.effective_date, sleepInfo.effectiveDate)
            set(it.sleep_hours, sleepInfo.sleepHours)
            set(it.bed_time, sleepInfo.bedTime)
            set(it.negative_thoughts_flag, sleepInfo.negativeThoughtsFlag)
            set(it.anxious_flag, sleepInfo.anxiousFlag)
            set(it.sleep_straight_flag, sleepInfo.sleepStraightFlag)
            set(it.sleep_notes, sleepInfo.sleepNotes)
        }
    }
}