package com.losrobotines.nuralign.feature_login.presentation.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_login.presentation.utils.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel(){

    private val _loginFlow = MutableStateFlow<LoginState<FirebaseUser>?>(null)
    val loginFlow: StateFlow<LoginState<FirebaseUser>?> = _loginFlow


    private val _message = MutableLiveData("")
    var message = _message

    private val _messageResetPass = MutableLiveData("")
    var messageResetPass = _messageResetPass

    private val _emailForgottenPassword = MutableLiveData("")
    var emailForgottenPassword = _emailForgottenPassword

    init {
        if (repository.currentUser!=null){
            if (repository.currentUser!!.isEmailVerified) {
                _loginFlow.value = LoginState.Success(repository.currentUser!!)
            }else{
                _loginFlow.value = LoginState.EmailNotVerified
                repository.logout()
            }
        }
    }


    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = LoginState.Loading
        val result = repository.login(email, password)
        val isEmailVerified = repository.currentUser.let { it!!.isEmailVerified }
        if(isEmailVerified)_loginFlow.value = result else{
            _message.value = "El email no esta verificado"
            _loginFlow.value = LoginState.EmailNotVerified
        }
    }


    fun logout() {
        repository.logout()
        _loginFlow.value = null
    }

    fun sendEmailForNewPassword(email: String){
        repository.sendPasswordResetEmail(email)
        _messageResetPass.value = "Email enviado"
    }

    fun setEmail(email: String){
        _emailForgottenPassword.value = email
    }


}