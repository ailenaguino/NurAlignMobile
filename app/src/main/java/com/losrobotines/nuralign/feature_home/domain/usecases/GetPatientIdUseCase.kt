package com.losrobotines.nuralign.feature_home.domain.usecases

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USER = "users"
private const val ID = "id"

class GetPatientIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Short {
        val currentUser = authRepository.getCurrentUserId()
        val doc = Firebase.firestore.collection(USER).document(currentUser!!)

        currentUser.let {
            return doc.get().await().getLong(ID)!!.toShort()
        }
    }
}
