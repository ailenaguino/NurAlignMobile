package com.losrobotines.nuralign.feature_medication.presentation.screens.tracker

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

    private val _isVisible = MutableLiveData(false)
    var isVisible: LiveData<Boolean> = _isVisible

    private val _route = MutableLiveData("")
    var route: LiveData<String> = _route

    private val _saveStatus = MutableLiveData<Result<Unit>>()

    fun loadMedicationTrackerInfo(patientMedicationId: Short) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val tracker = userService.getMedicationTrackerData(
                    patientMedicationId,
                    _currentDate.value
                )
                _medicationTrackerInfo.value = tracker
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                println(e)
            }
        }
    }

    fun updateMedicationTrackerInfo(medicationTrackerInfo: MedicationTrackerInfo) {
        viewModelScope.launch {
            _saveStatus.value = updateMedicationTrackerInfoUseCase(medicationTrackerInfo)

            val existingIndex =
                updatedTrackers.indexOfFirst { it.patientMedicationId == medicationTrackerInfo.patientMedicationId }
            if (existingIndex >= 0) {
                updatedTrackers[existingIndex] = medicationTrackerInfo
            } else {
                updatedTrackers.add(medicationTrackerInfo)
            }
        }
    }

    fun saveAllChanges() {
        viewModelScope.launch {
            updatedTrackers.forEach { tracker ->
                val result = saveMedicationTrackerInfoUseCase(tracker)

                if (result.isSuccess) {
                    _saveStatus.value = result
                } else {
                    println(result.exceptionOrNull())
                }
            }
            updatedTrackers.clear()
        }
    }

    fun setPatientMedicationId(id: Int?) {
        _patientMedicationId.intValue = id!!
    }

    fun checkNextTracker() {
        viewModelScope.launch {
            _route.value = checkNextTrackerToBeCompletedUseCase(userService.getPatientId().toInt())
        }
    }

    fun setIsVisible(value: Boolean) {
        _isVisible.value = value
    }

}