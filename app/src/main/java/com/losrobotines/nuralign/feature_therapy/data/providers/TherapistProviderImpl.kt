package com.losrobotines.nuralign.feature_therapy.data.providers

import android.util.Log
import com.losrobotines.nuralign.feature_therapy.data.dto.TherapistDto
import com.losrobotines.nuralign.feature_therapy.data.network.TherapistApiService
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapistProvider
import javax.inject.Inject

class TherapistProviderImpl @Inject constructor(
    private val apiService: TherapistApiService
) : TherapistProvider {
    override suspend fun getTherapistList(patientId: Short): List<TherapistInfo?> {
        val dto = apiService.getTherapistList(patientId)
        Log.d("TherapistProvider", "DtO Obtenido: $dto")
        return mapDataToDomain(dto)
    }

    override suspend fun saveTherapistInfo(therapistInfo: TherapistInfo?): Boolean {
        try {
            val dto = mapDomainToData(therapistInfo!!)
            apiService.insertTherapistInfo(dto)
            Log.d("TherapistProvider", "DtO Insertado: $dto")
            Log.d("TherapistProvider", "TherapistInfo Insertado: ${apiService.insertTherapistInfo(dto)}")
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun updateTherapistInfo(therapistInfo: TherapistInfo?): Boolean {
        try {
            val dto = mapDomainToData(therapistInfo!!)
            apiService.updateTherapistInfo(dto.id!!, dto)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun deleteTherapistInfo(patientId: Short, therapistId: Short): Boolean {
        try {
            apiService.deleteTherapistInfo(patientId, therapistId)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun mapDomainToData(therapistInfo: TherapistInfo): TherapistDto {
        return TherapistDto(
            id = therapistInfo.id,
            name = therapistInfo.name,
            lastName = therapistInfo.lastName,
            email = therapistInfo.email,
            phoneNumber = therapistInfo.phoneNumber,
            registeredFlag = therapistInfo.registeredFlag
        )
    }

    private fun mapDataToDomain(dto: List<TherapistDto?>): List<TherapistInfo?> {
        val list = mutableListOf<TherapistInfo?>()
        dto.let {
            for (therapist in it) {
                if (therapist != null) {
                    list.add(
                        TherapistInfo(
                            id = therapist.id,
                            name = therapist.name,
                            lastName = therapist.lastName,
                            email = therapist.email,
                            phoneNumber = therapist.phoneNumber,
                            registeredFlag = therapist.registeredFlag
                        )
                    )
                }
            }
        }
        return list
    }
}