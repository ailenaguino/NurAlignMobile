package com.losrobotines.nuralign.feature_medication.domain.providers

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo

interface MedicationProvider {

    suspend fun saveMedicationInfo(medicationInfo: MedicationInfo?): Boolean

    suspend fun getMedicationList(patientId: Short): List<MedicationInfo?>

    suspend fun updateMedicationInfo(newMedicationInfo: MedicationInfo?): Boolean

    suspend fun deleteMedicationInfo(patientMedicationId: Short): Boolean

}