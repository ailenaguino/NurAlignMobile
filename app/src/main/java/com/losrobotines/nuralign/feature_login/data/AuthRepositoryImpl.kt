package com.losrobotines.nuralign.feature_login.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.losrobotines.nuralign.feature_login.data.utils.await
import com.losrobotines.nuralign.feature_login.domain.AuthRepository
import com.losrobotines.nuralign.feature_login.presentation.screens.LoginState
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): LoginState<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            LoginState.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            LoginState.Failure(e)
        }
    }

    override suspend fun signup(
        email: String,
        password: String
    ): LoginState<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            LoginState.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            LoginState.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}