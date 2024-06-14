package com.losrobotines.nuralign.feature_sleep.domain

import com.losrobotines.nuralign.feature_sleep.data.dto.SleepTrackerDto
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import retrofit2.Response


interface SleepTrackerProvider {
    suspend fun saveSleepData(sleepInfo: SleepInfo) : Boolean
    suspend fun getSleepData(patientId: Int): SleepInfo?
    suspend fun getTodaysTracker(patientId: Int, date: String): SleepInfo?
}