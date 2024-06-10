package com.losrobotines.nuralign.feature_medication.presentation.screens.medication_tracker

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.usecases.EditExistingMedicationInListUseCase
import com.losrobotines.nuralign.feature_medication.domain.usecases.AddNewMedicationToListUseCase
import com.losrobotines.nuralign.feature_medication.domain.usecases.SaveMedicationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val userService: UserService,
    private val saveMedicationListUseCase: SaveMedicationListUseCase,
    private val addNewMedicationToListUseCase: AddNewMedicationToListUseCase,
    private val editExistingMedicationInListUseCase: EditExistingMedicationInListUseCase
) : ViewModel() {
    private val _medicationList = MutableLiveData<List<MedicationInfo?>>()
    val medicationList: LiveData<List<MedicationInfo?>> = _medicationList

    var medicationName = mutableStateOf("")
    val medicationGrammage = mutableIntStateOf(0)
    val medicationOptionalFlag = mutableStateOf("")

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _saveStatus = MutableLiveData<Result<Unit>>()
    val saveStatus: LiveData<Result<Unit>> = _saveStatus

    init {
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

    fun saveData() {
        viewModelScope.launch {
            try {
                _medicationList.value?.let { saveMedicationListUseCase(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addNewMedicationToList(medicationElement: MedicationInfo) {
        viewModelScope.launch {
            try {
                val result = _medicationList.value?.let {
                    addNewMedicationToListUseCase(medicationElement, it)
                }
                if (result?.isSuccess == true) {
                    _medicationList.value = result.getOrNull()!!.toList()
                    clearMedicationState()
                } else {
                    val errorMessage =
                        result?.exceptionOrNull()?.message ?: "Error adding medication"
                }
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
                _medicationList.value = editResult.getOrNull()!!
                _saveStatus.value = Result.success(Unit)
                clearMedicationState()
            } else {
                _saveStatus.value = Result.failure(Exception("Failed to edit medication"))
            }
        }
    }

    fun getCurrentPatientId(): Short {
        var id = 0.toShort()
        viewModelScope.launch { id = userService.getPatientId() }
        return id
    }

    fun clearMedicationState() {
        medicationName.value = ""
        medicationGrammage.intValue = 0
        medicationOptionalFlag.value = "N"
    }
}