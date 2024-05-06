package com.losrobotines.nuralign.feature_login.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.losrobotines.nuralign.feature_login.domain.AuthRepository
import com.losrobotines.nuralign.feature_login.presentation.screens.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel(){

    private val _signupFlow = MutableStateFlow<LoginState<FirebaseUser>?>(null)
    val signupFlow: StateFlow<LoginState<FirebaseUser>?> = _signupFlow

    fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        brithDate: String,
        sex: String
    ) = viewModelScope.launch {
        _signupFlow.value = LoginState.Loading
        val result = repository.signup(email, password)
        _signupFlow.value = result
    }

    fun logout() {
        repository.logout()
        _signupFlow.value = null
    }
}