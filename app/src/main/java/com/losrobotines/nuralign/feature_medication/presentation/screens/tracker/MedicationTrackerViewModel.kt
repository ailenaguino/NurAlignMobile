package com.losrobotines.nuralign.feature_medication.presentation.screens.tracker

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
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
    private val updateMedicationTrackerInfoUseCase: UpdateMedicationTrackerInfoUseCase,
) :
    ViewModel() {

    private val _medicationTrackerList = MutableLiveData<List<MedicationTrackerInfo?>>()
    val medicationTrackerList: LiveData<List<MedicationTrackerInfo?>> = _medicationTrackerList

    private val _patientMedicationId = mutableIntStateOf(0)
    val patientMedicationId: State<Int?> = _patientMedicationId

    private val _currentDate = mutableStateOf(userService.getCurrentDate())

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
                    _errorMessage.value =
                        "Error al cargar el seguimiento de la toma de medicamentos"
                    _isLoading.value = false
                    currentList.add(null)
                }
            }
            _medicationTrackerList.value = currentList
            _isLoading.value = false
        }
    }

    fun saveOrUpdateMedicationTracker() {
        viewModelScope.launch {
            val trackerToSaveOrUpdate = _medicationTrackerList.value ?: emptyList()

            trackerToSaveOrUpdate.forEach { tracker ->
                tracker?.let {
                    val checkExistingTracker =
                        userService.checkExistingTracker(
                            tracker.patientMedicationId,
                            _currentDate.value
                        )

                    if (!checkExistingTracker) {
                        val result = saveMedicationTrackerInfoUseCase(tracker)

                        if (result.isFailure) {
                            Log.e(
                                "MedicationTrackerViewModel",
                                "Error al guardar el seguimiento de la toma de medicamentos"
                            )
                            _errorMessage.value =
                                "Error al guardar el seguimiento de la toma de medicamentos"
                        }
                    } else {
                        val result = updateMedicationTrackerInfoUseCase(tracker)

                        if (result.isFailure) {
                            Log.e(
                                "MedicationTrackerViewModel",
                                "Error al actualizar el seguimiento de la toma de medicamentos"
                            )
                            _errorMessage.value =
                                "Error al actualizar el seguimiento de la toma de medicamentos"
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