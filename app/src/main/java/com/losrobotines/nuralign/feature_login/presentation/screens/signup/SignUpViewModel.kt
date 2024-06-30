package com.losrobotines.nuralign.feature_login.presentation.screens.signup

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_login.domain.models.PatientInfo
import com.losrobotines.nuralign.feature_login.domain.usecases.PreparePatientDataToBeSavedUseCase
import com.losrobotines.nuralign.feature_login.presentation.utils.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val preparePatientDataToBeSavedUseCase: PreparePatientDataToBeSavedUseCase
) : ViewModel() {

    private val _signupFlow = MutableStateFlow<LoginState<FirebaseUser>?>(null)
    val signupFlow: StateFlow<LoginState<FirebaseUser>?> = _signupFlow

    private val _message = MutableLiveData("")
    var message = _message

    @SuppressLint("NewApi")
    fun signup(email: String, password: String, firstName: String,
               lastName: String, birthDate: String, sex: String) {
        viewModelScope.launch {
            _signupFlow.value = LoginState.Loading
            val isSigned = repository.signup(email, password)
            when(isSigned){
                is LoginState.Success -> _message.value = "Email de verificación enviado"
                is LoginState.Failure -> _message.value = "Ocurrió un error"
                else -> _message.value = "¿Esta cargando I guess?"
            }
            _signupFlow.value = isSigned
            if (_signupFlow.value is LoginState.Success) {
                preparePatientDataToBeSavedUseCase(PatientInfo(
                    email, password, firstName, lastName, birthDate, sex, firstName, "Y", "Y"
                ))
            }
        }
    }

}