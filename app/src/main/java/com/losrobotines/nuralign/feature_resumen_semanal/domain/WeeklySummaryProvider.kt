package com.losrobotines.nuralign.feature_resumen_semanal.domain

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo

interface WeeklySummaryProvider {

    suspend fun getMoodTracker(patientId: Short, date: String): MoodTrackerInfo?


    suspend fun getSleepTracker(patientId: Short, date: String): SleepInfo?


    suspend fun getMedicationTracker(patientId: Short, date: String): MedicationTrackerInfo?

    suspend fun getMedicationListInfo(patientId: Short, medicationId: Short): MedicationInfo?

}