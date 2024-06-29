package com.losrobotines.nuralign.feature_therapy.data.providers

import com.losrobotines.nuralign.feature_therapy.data.dto.TherapySessionDto
import com.losrobotines.nuralign.feature_therapy.data.network.TherapySessionApiService
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapySessionProvider
import javax.inject.Inject

class TherapySessionProviderImpl @Inject constructor(
    private val apiService: TherapySessionApiService
) : TherapySessionProvider {
    override suspend fun getTherapySessionList(
        patientId: Short,
        therapistId: Short
    ): List<TherapySessionInfo?> {
        val dto = apiService.getTherapySessionList(patientId, therapistId)
        return mapDataToDomain(dto)
    }

    override suspend fun getTherapySessionInfo(therapySessionId: Short): TherapySessionInfo? {
        try {
            val dto = apiService.getTherapySessionInfo(therapySessionId)
            return mapDataToDomain(dto)
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun saveTherapySessionInfo(therapySessionInfo: TherapySessionInfo?): Boolean {
        try {
            val dto = mapDomainToData(therapySessionInfo!!)
            apiService.insertTherapySessionInfo(dto)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun updateTherapySessionInfo(therapySessionInfo: TherapySessionInfo?): Boolean {
        try {
            val dto = mapDomainToData(therapySessionInfo!!)
            apiService.updateTherapySessionInfo(
                dto.patientId,
                dto.therapistId,
                dto.effectiveDate,
                dto
            )
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun mapDomainToData(therapySessionInfo: TherapySessionInfo): TherapySessionDto {
        return TherapySessionDto(
            id = therapySessionInfo.id,
            patientId = therapySessionInfo.patientId,
            therapistId = therapySessionInfo.therapistId,
            effectiveDate = therapySessionInfo.sessionDate,
            sessionTime = therapySessionInfo.sessionTime,
            preSessionNotes = therapySessionInfo.preSessionNotes ?: "",
            postSessionNotes = therapySessionInfo.postSessionNotes ?: "",
            sessionFeel = therapySessionInfo.sessionFeel ?: ""
        )
    }

    private fun mapDataToDomain(therapySessionDto: TherapySessionDto): TherapySessionInfo {
        return TherapySessionInfo(
            id = therapySessionDto.id,
            patientId = therapySessionDto.patientId,
            therapistId = therapySessionDto.therapistId,
            sessionDate = therapySessionDto.effectiveDate,
            sessionTime = therapySessionDto.sessionTime,
            preSessionNotes = therapySessionDto.preSessionNotes ?: "",
            postSessionNotes = therapySessionDto.postSessionNotes ?: "",
            sessionFeel = therapySessionDto.sessionFeel ?: ""
        )
    }

    private fun mapDataToDomain(dto: List<TherapySessionDto?>): List<TherapySessionInfo?> {
        val list = mutableListOf<TherapySessionInfo?>()
        dto.let {
            for (session in it) {
                if (session != null) {
                    list.add(
                        TherapySessionInfo(
                            id = session.id,
                            patientId = session.patientId,
                            therapistId = session.therapistId,
                            sessionDate = session.effectiveDate,
                            sessionTime = session.sessionTime,
                            preSessionNotes = session.preSessionNotes ?: "",
                            postSessionNotes = session.postSessionNotes ?: "",
                            sessionFeel = session.sessionFeel ?: ""
                        )
                    )
                }
            }
        }
        return list
    }
}