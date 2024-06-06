package com.losrobotines.nuralign.feature_medication.domain.providers

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo

interface MedicationRepository {

    suspend fun saveMedicationInfo(medicationInfo: List<MedicationInfo?>)

    suspend fun getMedicationList(patientId: Short): MutableList<MedicationInfo?>

}