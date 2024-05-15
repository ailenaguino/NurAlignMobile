package com.losrobotines.nuralign.feature_sleep.data

import com.losrobotines.nuralign.feature_sleep.data.models.SleepTracker
import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ktorm.database.Database
import org.ktorm.dsl.insert

class SleepRepositoryImpl : SleepRepository {

    private lateinit var database: Database
    override suspend fun saveSleepData(sleepInfo: SleepInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                databaseConnect()

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
            } catch (e: Error){
                println(e)
            }
        }
    }

    private fun databaseConnect() {
        database = Database.connect(
            "jdbc:mariadb://77.37.69.38:3306/nuralign",
            user = "root",
            password = "/rt(Chw[-A(K@y8("
        )
    }
}