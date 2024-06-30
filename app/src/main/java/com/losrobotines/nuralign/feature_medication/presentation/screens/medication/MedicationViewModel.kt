package com.losrobotines.nuralign.feature_medication.presentation.screens.medication

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.usecases.medication.EditExistingMedicationInListUseCase
import com.losrobotines.nuralign.feature_medication.domain.usecases.medication.RemoveMedicationFromListUseCase
import com.losrobotines.nuralign.feature_medication.domain.usecases.medication.SaveMedicationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val userService: UserService,
    private val saveMedicationInfoUseCase: SaveMedicationInfoUseCase,
    private val editExistingMedicationInListUseCase: EditExistingMedicationInListUseCase,
    private val removeMedicationFromListUseCase: RemoveMedicationFromListUseCase
) : ViewModel() {
    private val _patientId = mutableStateOf<Short>(0)
    val patientId: State<Short> = _patientId

    private val _medicationList = MutableLiveData<List<MedicationInfo?>>()
    val medicationList: LiveData<List<MedicationInfo?>> = _medicationList

    var medicationName = mutableStateOf("")
    val medicationGrammage = mutableIntStateOf(0)
    val medicationOptionalFlag = mutableStateOf("")

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _saveStatus = MutableLiveData<Result<Unit>>()

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        getCurrentPatientId()
    }

    private fun loadMedicationList() {
        viewModelScope.launch {
            val result = userService.getMedicationList(_patientId.value)
            _isLoading.value = true

            if (result.isSuccess) {
                _medicationList.value = result.getOrNull()!!
                _isLoading.value = false
            } else {
                Log.e("MedicationViewModel", "Error loading medications")
                _errorMessage.value = "Error al cargar las medicaciones"
                _isLoading.value = false
            }
        }
    }

    fun saveData(medicationInfo: MedicationInfo): Result<Unit> {
        return runBlocking {
            val result = saveMedicationInfoUseCase(medicationInfo)
            if (result.isSuccess) {
                loadMedicationList()
                _saveStatus.value = Result.success(Unit)
                clearMedicationState()
                Result.success(Unit)
            } else {
                Log.e("MedicationViewModel", "Error saving ${medicationInfo.medicationName}")
                _errorMessage.value =
                    "Error al guardar la medicación ${medicationInfo.medicationName}"
                Result.failure(Exception("Failed to save medication"))
            }
        }
    }

    fun editExistingMedication(medicationElement: MedicationInfo) {
        viewModelScope.launch {
            val updatedMedication = medicationElement.copy(
                medicationName = medicationName.value,
                medicationGrammage = medicationGrammage.intValue,
                medicationOptionalFlag = medicationOptionalFlag.value
            )

            val result = editExistingMedicationInListUseCase(
                updatedMedication.medicationName,
                updatedMedication.medicationGrammage,
                updatedMedication.medicationOptionalFlag,
                medicationElement,
                _medicationList.value!!
            )

            if (result.isSuccess) {
                loadMedicationList()
                _saveStatus.value = Result.success(Unit)
                clearMedicationState()
            } else {
                _saveStatus.value = Result.failure(Exception("Failed to edit medication"))
                _errorMessage.value =
                    "Error al editar la medicación ${medicationElement.medicationName}"
            }
        }
    }

    fun removeMedicationFromList(medicationElement: MedicationInfo) {
        viewModelScope.launch {
            val result = removeMedicationFromListUseCase(medicationElement)

            if (result.isSuccess) {
                loadMedicationList()
                clearMedicationState()
            } else {
                Log.e(
                    "MedicationViewModel",
                    result.exceptionOrNull()?.message ?: "Error removing medication"
                )
                _errorMessage.value =
                    "Error al eliminar la medicación ${medicationElement.medicationName}"
            }
        }
    }

    private fun getCurrentPatientId() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = userService.getPatientId()

            if (result.isSuccess) {
                _patientId.value = result.getOrNull()!!
                loadMedicationList()
            } else {
                Log.e(
                    "MedicationViewModel",
                    "Error getting patient ID: ${result.exceptionOrNull()?.message}"
                )
                _errorMessage.value = "Error al obtener el ID del paciente"
                _isLoading.value = false
            }
        }
    }

    fun clearMedicationState() {
        medicationName.value = ""
        medicationGrammage.intValue = 0
        medicationOptionalFlag.value = ""
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}