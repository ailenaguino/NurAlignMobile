package com.losrobotines.nuralign.feature_login.presentation.screens.signup

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
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
    private val preparePatientDataToBeSavedUseCase: PreparePatientDataToBeSavedUseCase,
) : ViewModel() {

    private val _signupFlow = MutableStateFlow<LoginState<FirebaseUser>?>(null)
    val signupFlow: StateFlow<LoginState<FirebaseUser>?> = _signupFlow

    private val _email = MutableLiveData("")
    var email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    var password: LiveData<String> = _password

    private val _firstName = MutableLiveData("")
    var firstName: LiveData<String> = _firstName

    private val _lastName = MutableLiveData("")
    var lastName: LiveData<String> = _lastName

    private val _birthDate = MutableLiveData("")
    var birthDate: LiveData<String> = _birthDate

    private val _sex = MutableLiveData("Seleccione su sexo")
    var sex: LiveData<String> = _sex


    fun onEmailChanged(emailValue: String) {
        _email.value = emailValue
    }

    fun onPasswordChanged(passValue: String) {
        _password.value = passValue
    }

    fun onFirstNameChanged(firstNameValue: String) {
        _firstName.value = firstNameValue
    }

    fun onLastNameChanged(lastNameValue: String) {
        _lastName.value = lastNameValue
    }

    fun onBirthDateChanged(birthdateValue: String) {
        _birthDate.value = birthdateValue
    }

    fun onSexChanged(sexValue: String) {
        _sex.value = sexValue
    }

    @SuppressLint("NewApi")
    fun signup() {
        viewModelScope.launch {
            _signupFlow.value = LoginState.Loading
            _signupFlow.value = repository.signup(email.value!!, password.value!!)
            if (_signupFlow.value is LoginState.Success) {
                preparePatientDataToBeSavedUseCase(
                    PatientInfo(
                        email.value!!,
                        password.value!!,
                        firstName.value!!,
                        lastName.value!!,
                        birthDate.value!!,
                        sex.value!!,
                        firstName.value!!,
                        "Y",
                        "Y"
                    )
                )
            }
        }
    }


    fun logout() {
        repository.logout()
        _signupFlow.value = null
    }
}