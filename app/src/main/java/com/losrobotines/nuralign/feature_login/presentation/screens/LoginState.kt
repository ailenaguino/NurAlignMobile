package com.losrobotines.nuralign.feature_login.presentation.screens

sealed class LoginState<out R> {
    data class Success<out R>(val result: R) : LoginState<R>()
    data class Failure(val exception: Exception) : LoginState<Nothing>()
    object Loading : LoginState<Nothing>()
}