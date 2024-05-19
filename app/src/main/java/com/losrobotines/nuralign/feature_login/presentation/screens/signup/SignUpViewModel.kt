package com.losrobotines.nuralign.feature_login.presentation.screens.signup

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_login.domain.providers.SignUpProvider
import com.losrobotines.nuralign.feature_login.domain.models.PatientInfo
import com.losrobotines.nuralign.feature_login.domain.usecases.PreparePatientDataToBeSavedUseCase
import com.losrobotines.nuralign.feature_login.presentation.utils.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val preparePatientData: PreparePatientDataToBeSavedUseCase
) : ViewModel() {

    private val _signupFlow = MutableStateFlow<LoginState<FirebaseUser>?>(null)
    val signupFlow: StateFlow<LoginState<FirebaseUser>?> = _signupFlow

    @SuppressLint("NewApi")
    fun signup(email: String, password: String, firstName: String,
               lastName: String, birthDate: String, sex: String) {
        viewModelScope.launch {
            _signupFlow.value = LoginState.Loading
            _signupFlow.value = repository.signup(email, password)
            if (_signupFlow.value is LoginState.Success) {
                preparePatientData(PatientInfo(
                    email, password, firstName, lastName, birthDate, sex, firstName
                ))
            }
        }
    }


    fun logout() {
        repository.logout()
        _signupFlow.value = null
    }
}