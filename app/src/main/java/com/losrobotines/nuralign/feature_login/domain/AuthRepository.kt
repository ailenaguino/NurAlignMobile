package com.losrobotines.nuralign.feature_login.domain

import com.google.firebase.auth.FirebaseUser
import com.losrobotines.nuralign.feature_login.presentation.utils.LoginState

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): LoginState<FirebaseUser>
    suspend fun signup(
        email: String,
        password: String,
        //name: String, ESTO DESPUES GUARDAR EN LA DB
        //sexo: String/*, birthday :Date*/
    ): LoginState<FirebaseUser>

    fun logout()
}