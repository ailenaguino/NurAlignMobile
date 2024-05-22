package com.losrobotines.nuralign.feature_login.data.providers

import com.losrobotines.nuralign.feature_login.data.dto.PatientDto
import com.losrobotines.nuralign.feature_login.data.network.PatientApiService
import com.losrobotines.nuralign.feature_login.domain.providers.PatientProvider
import com.losrobotines.nuralign.feature_login.domain.models.PatientInfo
import com.losrobotines.nuralign.feature_login.domain.usecases.SavePatientIdOnFirestoreUseCase
import javax.inject.Inject

class PatientProviderImpl @Inject constructor(private val apiService: PatientApiService) : PatientProvider {
    override suspend fun savePatientData(patientInfo: PatientInfo) {
        val dto = mapDomainToData(patientInfo)
        val id = apiService.insertPatientInfoIntoDatabase(dto).patientId
        SavePatientIdOnFirestoreUseCase().invoke(id)
    }

    private fun mapDomainToData(patientInfo: PatientInfo): PatientDto{
        return PatientDto(
            email = patientInfo.email,
            password = patientInfo.password,
            firstName = patientInfo.firstName,
            lastName = patientInfo.lastName,
            birthDate = patientInfo.birthDate,
            sex = patientInfo.sex,
            nickname = patientInfo.nickname,
            generalNotifications = patientInfo.generalNotifications,
            companionNotifications = patientInfo.companionNotifications
        )
    }
}