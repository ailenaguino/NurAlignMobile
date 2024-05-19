package com.losrobotines.nuralign.feature_login.domain.usecases

import com.losrobotines.nuralign.feature_login.domain.models.PatientInfo
import com.losrobotines.nuralign.feature_login.domain.providers.SignUpProvider
import javax.inject.Inject

class PreparePatientDataToBeSavedUseCase @Inject constructor(
    private val signUpProvider: SignUpProvider,
    private val formatBirthDateUseCase: FormatBirthDateUseCase,
    private val formatSexUseCase: FormatSexUseCase
) {
    suspend operator fun invoke(patientInfo: PatientInfo) {
        patientInfo.birthDate = formatBirthDateUseCase(patientInfo.birthDate) ?: "X"
        patientInfo.sex = formatSexUseCase(patientInfo.sex)
        signUpProvider.savePatientData(patientInfo)
    }
}