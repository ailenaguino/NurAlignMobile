package com.losrobotines.nuralign.feature_login.data.providers

import com.losrobotines.nuralign.feature_login.data.dto.PatientDto
import com.losrobotines.nuralign.feature_login.data.network.PatientApiService
import com.losrobotines.nuralign.feature_login.domain.providers.PatientProvider
import com.losrobotines.nuralign.feature_login.domain.models.PatientInfo
import javax.inject.Inject

class PatientProviderImpl @Inject constructor(private val apiService: PatientApiService) : PatientProvider {
    override suspend fun savePatientData(patientInfo: PatientInfo):Short {
        try {
            val dto = mapDomainToData(patientInfo)
            return apiService.insertPatientInfoIntoDatabase(dto).patientId

        }catch (e:Exception){
            e.printStackTrace()
        }
        return 0
    }

    private fun mapDomainToData(patientInfo: PatientInfo): PatientDto{
        return PatientDto(
            email = patientInfo.email,
            password = patientInfo.password,
            firstName = patientInfo.firstName,
            lastName = patientInfo.lastName,
            birtDate = patientInfo.birthDate,
            sex = patientInfo.sex,
            nickname = patientInfo.nickname
        )
    }
}