package com.losrobotines.nuralign.feature_medication.domain.providers

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo

interface MedicationTrackerProvider {
    suspend fun getMedicationTrackerData(patientId: Short): MedicationTrackerInfo?
    suspend fun saveMedicationTrackerData(medicationTrackerInfo: MedicationTrackerInfo): Boolean
    suspend fun getTodaysTracker(patientId: Int, date: String): MedicationTrackerInfo?
}