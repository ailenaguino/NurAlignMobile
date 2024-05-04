package com.losrobotines.nuralign.feature_login.presentation.screens.login

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
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel(){


    private val _loginFlow = MutableStateFlow<LoginState<FirebaseUser>?>(null)
    val loginFlow: StateFlow<LoginState<FirebaseUser>?> = _loginFlow

    val currentUser: FirebaseUser? get() = repository.currentUser

    init {
        if (repository.currentUser!=null){
            _loginFlow.value= LoginState.Success(repository.currentUser!!)
        }
    }


    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = LoginState.Loading
        val result = repository.login(email, password)
        _loginFlow.value = result
    }


    fun logout() {
        repository.logout()
        _loginFlow.value = null
    }


}