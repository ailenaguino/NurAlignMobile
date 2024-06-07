package com.losrobotines.nuralign.feature_sleep.domain

import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo


interface SleepTrackerProvider {
    suspend fun saveSleepData(sleepInfo: SleepInfo)
    suspend fun getSleepData(patientId: Int): SleepInfo?
    suspend fun getTodaysTracker(patientId: Int, date: String): SleepInfo?
}