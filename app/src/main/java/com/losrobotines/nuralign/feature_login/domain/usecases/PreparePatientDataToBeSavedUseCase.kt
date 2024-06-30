package com.losrobotines.nuralign.feature_login.domain.usecases

import com.losrobotines.nuralign.feature_login.domain.models.PatientInfo
import com.losrobotines.nuralign.feature_login.domain.providers.PatientProvider
import javax.inject.Inject

class PreparePatientDataToBeSavedUseCase @Inject constructor(
    private val patientProvider: PatientProvider,
    private val formatBirthDateUseCase: FormatBirthDateUseCase,
    private val formatSexUseCase: FormatSexUseCase
) {
    suspend operator fun invoke(patientInfo: PatientInfo) {
        patientInfo.birthDate = formatBirthDateUseCase(patientInfo.birthDate) ?: "X"
        patientInfo.sex = formatSexUseCase(patientInfo.sex)
        patientProvider.savePatientInfo(patientInfo)
    }
}