package com.losrobotines.nuralign.feature_medication.domain.providers

interface MedicationRepository {

    suspend fun saveMedicationInfo(medicationInfo: MutableList<MedicationInfo>)

    suspend fun getMedicationList(patientId: Short): List<MedicationInfo?>

}