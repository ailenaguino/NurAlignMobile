package com.losrobotines.nuralign.feature_therapy.presentation.screens.session_history

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TherapySessionHistoryViewModel @Inject constructor(
    private val userService: UserService
) : ViewModel() {
    private val _patientId = mutableStateOf<Short>(0)
    val patientId: State<Short> = _patientId

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _sessionHistoryList = MutableLiveData<List<TherapySessionInfo?>>()
    val sessionHistoryList: LiveData<List<TherapySessionInfo?>> = _sessionHistoryList

    private val _selectedTherapist = MutableLiveData<TherapistInfo>()
    val selectedTherapist: LiveData<TherapistInfo> = _selectedTherapist

    fun loadTherapySessionToEdit(therapistInfo: TherapistInfo) {
        viewModelScope.launch {
            _selectedTherapist.value = therapistInfo
            getCurrentPatientId()
        }
    }

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private fun loadTherapySessionList() {
        viewModelScope.launch {
            val result = userService.getTherapySessionList(
                _patientId.value,
                _selectedTherapist.value?.therapistId ?: 0
            )
            _isLoading.value = true

            if (result.isSuccess) {
                _sessionHistoryList.value = result.getOrNull()!!
                _isLoading.value = false
            } else {
                _errorMessage.value = "Error al cargar las sesiones"
                _isLoading.value = false
            }
        }
    }

    private fun getCurrentPatientId() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = userService.getPatientId()

            if (result.isSuccess) {
                _patientId.value = result.getOrNull()!!
                loadTherapySessionList()
            } else {
                Log.e(
                    "TherapistViewModel",
                    "Error getting patient ID: ${result.exceptionOrNull()?.message}"
                )
                _errorMessage.value = "Error al obtener el ID del paciente"
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

}