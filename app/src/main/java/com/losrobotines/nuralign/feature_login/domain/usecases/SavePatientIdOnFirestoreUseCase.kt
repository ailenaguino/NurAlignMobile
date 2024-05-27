package com.losrobotines.nuralign.feature_login.domain.usecases

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_login.domain.models.User
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_login.domain.providers.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavePatientIdOnFirestoreUseCase @Inject constructor(private val authRepository: AuthRepository,
                                                          private val userRepository: UserRepository) {

    operator fun invoke(domainUser: Short) {
        val currentUser = authRepository.getCurrentUserId()
        CoroutineScope(Dispatchers.IO).launch {
            currentUser?.let {
                userRepository.save(it, domainUser.toLong())
            } //?: por si es nulo hacer algo
        }
    }
}
//Lo que haria el profe:
//Programacion reactiva buscar