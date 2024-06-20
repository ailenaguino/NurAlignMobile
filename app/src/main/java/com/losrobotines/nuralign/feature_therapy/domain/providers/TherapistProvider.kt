package com.losrobotines.nuralign.feature_therapy.domain.providers

import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo

interface TherapistProvider {
    suspend fun getTherapistList(patientId: Short): List<TherapistInfo?>
    suspend fun saveTherapistInfo(therapistInfo: TherapistInfo?): Boolean
    suspend fun updateTherapistInfo(therapistInfo: TherapistInfo?): Boolean
    suspend fun deleteTherapistInfo(patientId: Short, therapistId: Short): Boolean
}