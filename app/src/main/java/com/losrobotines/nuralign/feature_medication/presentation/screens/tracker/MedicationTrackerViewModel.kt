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

    private val _medicationTrackerList = MutableLiveData<List<MedicationTrackerInfo?>>()
    val medicationTrackerList: LiveData<List<MedicationTrackerInfo?>> = _medicationTrackerList

    private val _patientMedicationId = mutableIntStateOf(0)
    val patientMedicationId: State<Int?> = _patientMedicationId

    private val _currentDate = mutableStateOf(userService.getCurrentDate())
    val currentDate: String = _currentDate.value

    private val _isLoading = MutableLiveData(false)

    private val _isVisible = MutableLiveData(false)
    var isVisible: LiveData<Boolean> = _isVisible

    private val _route = MutableLiveData("")
    var route: LiveData<String> = _route

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadMedicationTrackerInfo(patientMedicationIds: List<Short>) {
        viewModelScope.launch {
            _isLoading.value = true

            val currentList = mutableListOf<MedicationTrackerInfo?>()

            patientMedicationIds.forEach {
                val result =
                    userService.getMedicationTrackerData(it, _currentDate.value)
                if (result.isSuccess) {
                    val trackerList = result.getOrNull()
                    if (trackerList == null) {
                        val newTracker = MedicationTrackerInfo(it, _currentDate.value, "N")
                        currentList.add(newTracker)
                    } else {
                        currentList.add(trackerList)
                    }
                } else {
                    //Este error sale cuando no se encuentra el seguimiento para una medicación
                    //Es porque el get devuelve 500, cuando devuelva 204 no debería mostrar más este error.
                    _errorMessage.postValue("Error al cargar el seguimiento de la toma de medicamentos")
                    _isLoading.postValue(false)
                    currentList.add(null)
                }
            }
            _medicationTrackerList.postValue(currentList)
            _isLoading.postValue(false)
        }
    }

    fun saveOrUpdateMedicationTracker() {
        viewModelScope.launch {
            val trackerToSaveOrUpdate = _medicationTrackerList.value ?: emptyList()

            trackerToSaveOrUpdate.forEach { tracker ->
                tracker?.let {
                    val existingTracker = _medicationTrackerList.value?.find {
                        it?.patientMedicationId == tracker.patientMedicationId
                    }
                    if (existingTracker == null) {
                        val result = saveMedicationTrackerInfoUseCase(tracker)
                        if (result.isFailure) {
                            Log.e(
                                "MedicationTrackerViewModel",
                                "Error al guardar el seguimiento de la toma de medicamentos"
                            )
                            _errorMessage.postValue("Error al guardar el seguimiento de la toma de medicamentos")
                        }
                    } else {
                        val result = updateMedicationTrackerInfoUseCase(tracker)
                        if (result.isFailure) {
                            Log.e(
                                "MedicationTrackerViewModel",
                                "Error al actualizar el seguimiento de la toma de medicamentos"
                            )
                            _errorMessage.postValue("Error al actualizar el seguimiento de la toma de medicamentos")
                        }
                    }

                    loadMedicationTrackerInfo(trackerToSaveOrUpdate.mapNotNull {
                        it?.patientMedicationId
                    })
                }
            }
        }
    }

    fun setPatientMedicationId(id: Int?) {
        _patientMedicationId.intValue = id!!
    }

    fun updateTakenFlag(id: Short, takenFlag: String) {
        viewModelScope.launch {
            val currentTrackerList = _medicationTrackerList.value ?: emptyList()
            val index = currentTrackerList.indexOfFirst { it?.patientMedicationId == id }

            if (index >= 0) {
                val updatedTracker = currentTrackerList[index]?.copy(takenFlag = takenFlag)
                val updatedList = currentTrackerList.toMutableList()
                updatedList[index] = updatedTracker

                _medicationTrackerList.postValue(updatedList)
            }
        }
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