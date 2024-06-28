package com.losrobotines.nuralign.feature_therapy.domain.providers

import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo

interface TherapySessionProvider {
    suspend fun getTherapySessionList(patientId: Short, therapistId: Short): List<TherapySessionInfo?>
    suspend fun getTherapySessionInfo(therapySessionId: Short): TherapySessionInfo?
    suspend fun saveTherapySessionInfo(therapySessionInfo: TherapySessionInfo?): Boolean
    suspend fun updateTherapySessionInfo(therapySessionInfo: TherapySessionInfo?): Boolean
}