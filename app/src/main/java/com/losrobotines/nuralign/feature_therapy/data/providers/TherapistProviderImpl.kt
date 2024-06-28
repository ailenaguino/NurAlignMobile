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
        val therapistList = mutableListOf<TherapistInfo>()
        val dto = apiService.getTherapistList(patientId)
        Log.d("TherapistProvider", "DtO Obtenido: $dto")
        dto.forEach {
            val r = getTherapistInfo(it?.therapistId!!)
            therapistList.add(r)
        }
        return therapistList
    }

    override suspend fun getTherapistInfo(therapistId: Short): TherapistInfo{
        val dto = apiService.getTherapistInfo(therapistId)
        return mapDataToDomain(dto)
    }

    override suspend fun saveTherapistInfo(therapistInfo: TherapistInfo?): Boolean {
        try {
            val dto = mapDomainToData(therapistInfo!!)
            val result = apiService.insertTherapistInfo(dto)
            Log.d("TherapistProvider", "DtO Insertado: $dto")
            Log.d("TherapistProvider", "TherapistInfo Insertado: $result")
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun updateTherapistInfo(therapistInfo: TherapistInfo?): Boolean {
        try {
            val dto = mapDomainToData(therapistInfo!!)
            apiService.updateTherapistInfo(dto.therapistId!!, dto)
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
            therapistId = therapistInfo.therapistId,
            patientId = therapistInfo.patientId,
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
                            therapistId = therapist.therapistId,
                            patientId = therapist.patientId?:0,
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

    private fun mapDataToDomain(therapist: TherapistDto): TherapistInfo {
        return TherapistInfo(
            therapistId = therapist.id,
            patientId = therapist.patientId?:0,
            name = therapist.name,
            lastName = therapist.lastName,
            email = therapist.email,
            phoneNumber = therapist.phoneNumber,
            registeredFlag = therapist.registeredFlag
        )
    }
}