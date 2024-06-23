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

    private val _updatedTrackers = mutableStateListOf<MedicationTrackerInfo>()

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

    fun loadMedicationTrackerInfo(patientMedicationId: Short) {
        viewModelScope.launch {
            _isLoading.value = true
            val result =
                userService.getMedicationTrackerData(patientMedicationId, _currentDate.value)
            val currentList = _medicationTrackerList.value ?: emptyList()
            val index = currentList.indexOfFirst { it?.patientMedicationId == patientMedicationId }

            if (result.isSuccess) {
                val updatedList = currentList.toMutableList()
                if (index >= 0) {
                    updatedList[index] = result.getOrNull()
                } else {
                    updatedList.add(result.getOrNull())
                }
                _medicationTrackerList.postValue(updatedList)
                _isLoading.postValue(false)
            } else {
                Log.e(
                    "MedicationTrackerViewModel",
                    "${result.exceptionOrNull()?.message}"
                )
                //Este error sale cuando no se encuentra el seguimiento para una medicación
                //Es porque el get devuelve 500, cuando devuelva 204 no debería mostrar más este error.
                _errorMessage.postValue("Error al cargar el seguimiento de la toma de medicamentos")
                _isLoading.postValue(false)
            }
        }
    }

    fun saveOrUpdateMedicationTracker() {
        viewModelScope.launch {
            val currentTrackerList = _medicationTrackerList.value ?: emptyList()
            val trackersToSave = _updatedTrackers.filter { tracker ->
                currentTrackerList.none {
                    it?.patientMedicationId == tracker.patientMedicationId
                }
            }.toList()

            val trackersToUpdate = _updatedTrackers.filter { tracker ->
                currentTrackerList.any {
                    it?.patientMedicationId == tracker.patientMedicationId
                }
            }.toList()

            _updatedTrackers.clear()

            if (trackersToSave.isNotEmpty()) {
                val results = trackersToSave.map {
                    saveMedicationTrackerInfoUseCase(it)
                }
                if (results.all { it.isSuccess }) {
                    trackersToSave.forEach {
                        loadMedicationTrackerInfo(it.patientMedicationId)
                    }
                } else {
                    results.forEachIndexed { index, result ->
                        if (result.isFailure) {
                            Log.e(
                                "MedicationTrackerViewModel",
                                "Error saving ${trackersToSave[index]} : ${result.exceptionOrNull()?.message}"
                            )
                        }
                    }
                    _errorMessage.value =
                        "Error al guardar el seguimiento de la toma de medicamentos"
                }
            } else if (trackersToUpdate.isNotEmpty()) {
                val results = trackersToUpdate.map {
                    updateMedicationTrackerInfoUseCase(it)
                }
                if (results.all { it.isSuccess }) {
                    trackersToUpdate.forEach {
                        loadMedicationTrackerInfo(it.patientMedicationId)
                    }
                } else {
                    results.forEachIndexed { index, result ->
                        if (result.isFailure) {
                            Log.e(
                                "MedicationTrackerViewModel",
                                "Error updating ${trackersToUpdate[index]} : ${result.exceptionOrNull()?.message}"
                            )
                        }
                    }
                }
            } else {
                _errorMessage.value = "No hay seguimiento de medicamentos para guardar"
            }
        }
    }

    fun setPatientMedicationId(id: Int?) {
        _patientMedicationId.intValue = id!!
    }

    fun addTrackerToUpdate(tracker: MedicationTrackerInfo) {
        _updatedTrackers.add(tracker)
    }

    fun updateTakenFlag(id: Short, takenFlag: String) {
        viewModelScope.launch {
            val currentTrackerList = _medicationTrackerList.value ?: emptyList()
            val index = currentTrackerList.indexOfFirst { it?.patientMedicationId == id }

            if (index >= 0) {
                val updatedTracker = currentTrackerList[index]?.copy(takenFlag = takenFlag)
                updatedTracker?.let {
                    addTrackerToUpdate(it)
                }

                val updatedList = currentTrackerList.toMutableList()
                updatedList[index] = updatedTracker

                _medicationTrackerList.value = updatedList
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