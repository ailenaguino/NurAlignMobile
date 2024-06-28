package com.losrobotines.nuralign.feature_therapy.presentation.screens.therapists

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.usecases.EditExistingTherapistInListUseCase
import com.losrobotines.nuralign.feature_therapy.domain.usecases.RemoveTherapistFromListUseCase
import com.losrobotines.nuralign.feature_therapy.domain.usecases.SaveTherapistInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TherapistViewModel @Inject constructor(
    private val userService: UserService,
    private val saveTherapistInfoUseCase: SaveTherapistInfoUseCase,
    private val editExistingTherapistInListUseCase: EditExistingTherapistInListUseCase,
    private val removeTherapistFromListUseCase: RemoveTherapistFromListUseCase
) : ViewModel() {
    private val _patientId = mutableStateOf<Short>(0)
    val patientId: State<Short> = _patientId

    private val _therapistList = MutableLiveData<List<TherapistInfo?>>()
    val therapistList: LiveData<List<TherapistInfo?>> = _therapistList

    val therapistFirstName = mutableStateOf("")
    val therapistLastName = mutableStateOf("")
    val therapistEmail = mutableStateOf("")
    val therapistPhone = mutableIntStateOf(0)

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _saveStatus = MutableLiveData<Result<Unit>>()

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        getCurrentPatientId()
    }

    private fun loadTherapistList() {
        viewModelScope.launch {
            val result = userService.getTherapistList(_patientId.value)
            _isLoading.value = true

            if (result.isSuccess) {
                _therapistList.value = result.getOrNull()!!
                _isLoading.value = false
            } else {
                _errorMessage.value = "Error al cargar los terapeutas"
                _isLoading.value = false
            }

        }
    }


    fun saveData(therapistInfo: TherapistInfo) {
        viewModelScope.launch {
            val result = saveTherapistInfoUseCase(therapistInfo)
            if (result.isSuccess) {
                loadTherapistList()
                _saveStatus.value = Result.success(Unit)
                clearTherapistState()
            } else {
                Log.e(
                    "TherapistViewModel",
                    "Error saving ${therapistInfo.name} ${therapistInfo.lastName}"
                )
                _errorMessage.value =
                    "Error al guardar al terapeuta ${therapistInfo.name} ${therapistInfo.lastName}"
            }

        }
    }

    fun editExistingTherapist(therapistInfo: TherapistInfo) {
        viewModelScope.launch {
            val result = editExistingTherapistInListUseCase(
                therapistFirstName.value,
                therapistLastName.value,
                therapistEmail.value,
                therapistPhone.intValue,
                therapistInfo,
                _therapistList.value!!
            )

            if (result.isSuccess) {
                loadTherapistList()
                _saveStatus.value = Result.success(Unit)
                clearTherapistState()
            } else {
                _saveStatus.value = Result.failure(Exception("Failed to edit therapist"))
                _errorMessage.value =
                    "Error al editar al terapeuta ${therapistInfo.name} ${therapistInfo.lastName}"
            }
        }
    }

    fun removeTherapistFromList(therapistInfo: TherapistInfo) {
        viewModelScope.launch {
            val result = removeTherapistFromListUseCase(_patientId.value, therapistInfo)

            if (result.isSuccess) {
                loadTherapistList()
                clearTherapistState()
            } else {
                Log.e(
                    "TherapistViewModel",
                    result.exceptionOrNull()?.message ?: "Error removing therapist"
                )
                _errorMessage.value =
                    "Error al eliminar al terapeuta ${therapistInfo.name} ${therapistInfo.lastName}"
            }
        }
    }

    private fun getCurrentPatientId() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = userService.getPatientId()

            if (result.isSuccess) {
                _patientId.value = result.getOrNull()!!
                loadTherapistList()
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

    fun clearTherapistState() {
        therapistFirstName.value = ""
        therapistLastName.value = ""
        therapistEmail.value = ""
        therapistPhone.intValue = 0
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}