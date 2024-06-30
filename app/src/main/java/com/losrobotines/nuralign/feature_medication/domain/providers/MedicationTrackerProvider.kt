package com.losrobotines.nuralign.feature_medication.domain.providers

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo

interface MedicationTrackerProvider {
    suspend fun getMedicationTrackerData(patientMedicationId: Short, effectiveDate: String): Result<MedicationTrackerInfo?>
    suspend fun saveMedicationTrackerData(medicationTrackerInfo: MedicationTrackerInfo): Result<Unit>
    suspend fun updateMedicationTrackerData(medicationTrackerInfo: MedicationTrackerInfo): Boolean
}