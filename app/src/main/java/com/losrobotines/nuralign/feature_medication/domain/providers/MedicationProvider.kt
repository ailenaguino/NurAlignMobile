package com.losrobotines.nuralign.feature_medication.domain.providers

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo

interface MedicationProvider {

    suspend fun saveMedicationList(medicationList: List<MedicationInfo?>): Boolean

    suspend fun getMedicationList(patientId: Short): List<MedicationInfo?>

}