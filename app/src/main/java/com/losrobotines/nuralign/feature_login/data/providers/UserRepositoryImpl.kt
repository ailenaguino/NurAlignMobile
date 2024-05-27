package com.losrobotines.nuralign.feature_login.data.providers

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_login.data.utils.await
import com.losrobotines.nuralign.feature_login.domain.models.User
import com.losrobotines.nuralign.feature_login.domain.providers.UserRepository

class UserRepositoryImpl : UserRepository {

    override fun save(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun save(currentUser: String, domainUser: Long) {
        Firebase.firestore.collection("users").document(currentUser)
            .set(hashMapOf("id" to domainUser.toInt())).await()
    }
}
//Investigar Task