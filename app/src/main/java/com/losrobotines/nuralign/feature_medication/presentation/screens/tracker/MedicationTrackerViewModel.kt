package com.losrobotines.nuralign.feature_medication.presentation.screens.tracker

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_home.domain.usecases.CheckNextTrackerToBeCompletedUseCase
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_medication.domain.usecases.tracker.SaveMedicationTrackerInfoUseCase
import com.losrobotines.nuralign.feature_medication.domain.usecases.tracker.UpdateMedicationTrackerInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationTrackerViewModel @Inject constructor(
    private val userService: UserService,
    private val checkNextTrackerToBeCompletedUseCase: CheckNextTrackerToBeCompletedUseCase,
    private val saveMedicationTrackerInfoUseCase: SaveMedicationTrackerInfoUseCase,
    private val updateMedicationTrackerInfoUseCase: UpdateMedicationTrackerInfoUseCase
) :
    ViewModel() {

    private val _medicationTrackerInfo = MutableLiveData<MedicationTrackerInfo?>()
    val medicationTrackerInfo: LiveData<MedicationTrackerInfo?> = _medicationTrackerInfo

    private val updatedTrackers = mutableStateListOf<MedicationTrackerInfo>()

    private val _patientMedicationId = mutableIntStateOf(0)
    val patientMedicationId: State<Int?> = _patientMedicationId

    private val _currentDate = mutableStateOf(userService.getCurrentDate())

    private val _isLoading = MutableLiveData(false)

    private val _saveStatus = MutableLiveData<Result<Unit>>()

    private val _isVisible = MutableLiveData(false)
    var isVisible: LiveData<Boolean> = _isVisible

    private val _route = MutableLiveData("")
    var route: LiveData<String> = _route

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadMedicationTrackerInfo(patientMedicationId: Short) {
        viewModelScope.launch {
            val result =
                userService.getMedicationTrackerData(patientMedicationId, _currentDate.value)
            _isLoading.value = true

            if (result.isSuccess) {
                val tracker = result.getOrNull()
                _medicationTrackerInfo.value = tracker
                _isLoading.value = false
            } else {
                Log.e(
                    "MedicationTrackerViewModel",
                    "${result.exceptionOrNull()?.message}"
                )
                _errorMessage.value = "Error al cargar el seguimiento de la toma de medicamentos"
                _isLoading.value = false
            }
        }
    }

    fun updateMedicationTrackerInfo(medicationTrackerInfo: MedicationTrackerInfo) {
        viewModelScope.launch {
            val result = updateMedicationTrackerInfoUseCase(medicationTrackerInfo)

            if (result.isSuccess) {
                _medicationTrackerInfo.value = result.getOrNull()
                _saveStatus.value = Result.success(Unit)
            } else {
                Log.e(
                    "MedicationTrackerViewModel",
                    "Error updating ${medicationTrackerInfo.patientMedicationId}: ${result.exceptionOrNull()?.message}"
                )
                _errorMessage.value = "Error al actualizar el seguimiento"
            }
        }
    }

    fun saveAllChanges() {
        viewModelScope.launch {
            updatedTrackers.forEach { tracker ->
                val result = saveMedicationTrackerInfoUseCase(tracker)

                if (result.isSuccess) {
                    _saveStatus.value = result

                    updatedTrackers.clear()
                } else {
                    Log.e(
                        "MedicationTrackerViewModel",
                        "Error saving ${tracker.patientMedicationId}: ${result.exceptionOrNull()?.message}"
                    )
                    _errorMessage.value =
                        "Error al guardar el seguimiento de la toma de medicamentos"
                }
            }
        }
    }

    fun setPatientMedicationId(id: Int?) {
        _patientMedicationId.intValue = id!!
    }

    fun checkNextTracker() {
        viewModelScope.launch {
            _route.value =
                checkNextTrackerToBeCompletedUseCase(_patientMedicationId.intValue)
        }
    }

    fun setIsVisible(value: Boolean) {
        _isVisible.value = value
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}