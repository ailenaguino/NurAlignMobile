package com.losrobotines.nuralign.feature_login.data.providers

import com.losrobotines.nuralign.feature_login.data.dto.PatientDto
import com.losrobotines.nuralign.feature_login.data.network.PatientApiService
import com.losrobotines.nuralign.feature_login.domain.providers.SignUpProvider
import com.losrobotines.nuralign.feature_login.domain.models.PatientInfo
import javax.inject.Inject

class SignUpProviderImpl @Inject constructor(private val apiService: PatientApiService) : SignUpProvider {
    override suspend fun savePatientData(patientInfo: PatientInfo) {
        try {
            val dto = mapDomainToData(patientInfo)
            apiService.insertPatientInfoIntoDatabase(dto)

        }catch (e:Exception){
            e.printStackTrace()
        }
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