package com.losrobotines.nuralign.feature_login.domain.providers

import com.losrobotines.nuralign.feature_login.domain.models.PatientInfo

interface SignUpProvider{

    suspend fun savePatientData(patientInfo: PatientInfo)
}