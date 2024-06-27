package com.losrobotines.nuralign.feature_login.presentation.utils

sealed class LoginState<out R> {
    data class Success<out R>(val result: R) : LoginState<R>()
    data class Failure(val exception: Exception) : LoginState<Nothing>()
    data object Loading : LoginState<Nothing>()
    data object EmailNotVerified: LoginState<Nothing>()
}