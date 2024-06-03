package com.losrobotines.nuralign.feature_medication.domain.usecases

import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationRepository
import javax.inject.Inject

class GetMedicationInfoFromDatabaseUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val medicationRepository: MedicationRepository
) {
    suspend operator fun invoke(id: Short): MutableList<MedicationInfo?> {
        lateinit var medicationList: MutableList<MedicationInfo?>
        val currentUser = authRepository.getCurrentUserId()
        currentUser?.let {
            medicationList = medicationRepository.getMedicationList(id)
        }
        return medicationList
    }
}