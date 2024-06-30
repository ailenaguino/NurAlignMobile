package com.losrobotines.nuralign.feature_therapy.domain.usecases.therapists

import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapistProvider
import javax.inject.Inject

class EditExistingTherapistInListUseCase @Inject constructor(
    private val therapistProvider: TherapistProvider
) {
    suspend operator fun invoke(
        newTherapistFirstName: String,
        newTherapistLastName: String,
        newTherapistEmail: String,
        newTherapistPhone: Int,
        oldTherapistInfo: TherapistInfo,
        therapistList: List<TherapistInfo?>
    ): Result<Unit> {
        return try {
            val updatedTherapist = therapistList.find {
                it == oldTherapistInfo
            }?.copy(
                name = newTherapistFirstName.ifBlank { oldTherapistInfo.name },
                lastName = newTherapistLastName.ifBlank { oldTherapistInfo.lastName },
                email = newTherapistEmail.ifBlank { oldTherapistInfo.email },
                phoneNumber = if (newTherapistPhone != 0) newTherapistPhone else oldTherapistInfo.phoneNumber
            )

            if (updatedTherapist != null) {
                therapistProvider.updateTherapistInfo(updatedTherapist)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Therapist not found in list"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
