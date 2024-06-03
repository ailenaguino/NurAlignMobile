package com.losrobotines.nuralign.feature_medication.domain.providers

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo

interface MedicationProvider {

    suspend fun saveMedicationData(medicationInfo: MedicationInfo)

}