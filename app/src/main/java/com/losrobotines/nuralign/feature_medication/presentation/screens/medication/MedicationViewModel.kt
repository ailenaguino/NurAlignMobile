package com.losrobotines.nuralign.feature_medication.presentation.screens.medication

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
    val medicationOptionalFlag = mutableStateOf("N")

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _saveStatus = MutableLiveData<Result<Unit>>()
    val saveStatus: LiveData<Result<Unit>> = _saveStatus

    init {
        getCurrentPatientId()
        loadMedicationList()
    }

    private fun loadMedicationList() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _medicationList.value = userService.getMedicationList(userService.getPatientId())
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveData(medicationInfo: MedicationInfo) {
        viewModelScope.launch {
            try {
                saveMedicationInfoUseCase(medicationInfo)
                loadMedicationList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editExistingMedication(medicationElement: MedicationInfo) {
        viewModelScope.launch {
            val editResult = editExistingMedicationInListUseCase(
                medicationName.value,
                medicationGrammage.intValue,
                medicationOptionalFlag.value,
                medicationElement,
                _medicationList.value!!
            )

            if (editResult.isSuccess) {
                loadMedicationList()
                _saveStatus.value = Result.success(Unit)
                clearMedicationState()
            } else {
                _saveStatus.value = Result.failure(Exception("Failed to edit medication"))
            }
        }
    }

    private fun getCurrentPatientId() {
        viewModelScope.launch { _patientId.value = userService.getPatientId() }
    }

    fun clearMedicationState() {
        medicationName.value = ""
        medicationGrammage.intValue = 0
        medicationOptionalFlag.value = ""
    }

    fun removeMedicationFromList(medicationElement: MedicationInfo) {
        viewModelScope.launch {
            val removeResult = removeMedicationFromListUseCase(medicationElement)

            if (removeResult.isSuccess) {
                loadMedicationList()
                clearMedicationState()
            } else {
                val errorMessage =
                    removeResult.exceptionOrNull()?.message ?: "Error removing medication"
            }
        }
    }
}