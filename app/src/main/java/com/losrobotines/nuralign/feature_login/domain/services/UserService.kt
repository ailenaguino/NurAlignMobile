package com.losrobotines.nuralign.feature_login.domain.services

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USER = "users"
private const val ID = "id"

class UserService @Inject constructor(
    private val authRepository: AuthRepository,
    private val medicationProvider: MedicationProvider
) {
    suspend fun getPatientId(): Short {
        val currentUser = authRepository.getCurrentUserId()
        val doc = Firebase.firestore.collection(USER).document(currentUser!!)

        currentUser.let {
            return doc.get().await().getLong(ID)!!.toShort()
        }
    }

    suspend fun getMedicationList(id: Short): List<MedicationInfo?> {
        return medicationProvider.getMedicationList(id)
    }

    suspend fun saveMedicationList(medicationInfo: List<MedicationInfo?>): Boolean {
        return medicationProvider.saveMedicationList(medicationInfo)
    }
}