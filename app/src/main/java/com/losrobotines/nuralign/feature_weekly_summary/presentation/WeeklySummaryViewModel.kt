package com.losrobotines.nuralign.feature_weekly_summary.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.CalculateAverageSleepHoursUseCase
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.CalculateWeeklyMoodAveragesUseCase
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.GetWeeklyMedicationTrackerInfoUseCase
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.GetWeeklyMoodTrackerInfoUseCase
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.GetWeeklySleepTrackerInfoUseCase
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.MoodTrackerAveragesLabels
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.GetWeeklyMedicationListInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@SuppressLint("NewApi")
@HiltViewModel
class WeeklySummaryViewModel @Inject constructor(
    private val userService: UserService,
    private val getWeeklyMoodTrackerInfoUseCase: GetWeeklyMoodTrackerInfoUseCase,
    private val getWeeklySleepTrackerInfoUseCase: GetWeeklySleepTrackerInfoUseCase,
    private val getWeeklyMedicationTrackerInfoUseCase: GetWeeklyMedicationTrackerInfoUseCase,
    private val calculateAverageSleepHoursUseCase: CalculateAverageSleepHoursUseCase,
    private val calculateWeeklyMoodAveragesUseCase: CalculateWeeklyMoodAveragesUseCase,
    private val getWeeklyMedicationListInfoUseCase: GetWeeklyMedicationListInfoUseCase
) : ViewModel() {

    private val _moodTrackerInfoList = MutableStateFlow<List<MoodTrackerInfo?>>(emptyList())
    val moodTrackerInfoList: StateFlow<List<MoodTrackerInfo?>> get() = _moodTrackerInfoList

    private val _sleepTrackerInfoList = MutableStateFlow<List<SleepInfo?>>(emptyList())
    val sleepTrackerInfoList: StateFlow<List<SleepInfo?>> get() = _sleepTrackerInfoList

    private val _medicationTrackerInfoList = MutableStateFlow<List<MedicationTrackerInfo?>>(emptyList())
    val medicationTrackerInfoList: StateFlow<List<MedicationTrackerInfo?>> get() = _medicationTrackerInfoList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _moodAveragesLabels = MutableStateFlow<MoodTrackerAveragesLabels?>(null)
    val moodAveragesLabels: StateFlow<MoodTrackerAveragesLabels?> get() = _moodAveragesLabels

    private val _medicationList = MutableStateFlow<List<MedicationInfo?>>(emptyList())
    val medicationList: StateFlow<List<MedicationInfo?>> get() = _medicationList

    init {
        updateData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                loadMedication()
                loadMoodTrackerInfo()
                loadSleepTrackerInfo()
            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar la información"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadMedication() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val patientId = userService.getPatientId().getOrNull()?.toInt() ?: 0
                val info = getWeeklyMedicationListInfoUseCase(patientId.toShort())
                Log.d("Medication", info.toString())
                _medicationList.value = info

                loadMedicationTrackerInfo(info.mapNotNull { it?.patientMedicationId })
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar la información del medication"
            } finally {
                _isLoading.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadMedicationTrackerInfo(medicationIds: List<Short>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val trackerInfoList = mutableListOf<MedicationTrackerInfo?>()

                medicationIds.forEach { medicationId ->
                    try {
                        val infoList = getWeeklyMedicationTrackerInfoUseCase(medicationId)
                        Log.d("MedicationTrackerInfo", "MedId: $medicationId Info: $infoList")
                        trackerInfoList.addAll(infoList)
                    } catch (e: Exception) {
                        Log.e("MedicationTrackerInfo", "Error loading tracker info for medId: $medicationId", e)
                        trackerInfoList.add(null)
                    }
                }
                _medicationTrackerInfoList.value = trackerInfoList
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar la información del medicationTracker"
            } finally {
                _isLoading.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadMoodTrackerInfo() {
        viewModelScope.launch {
            try {
                val patientId = userService.getPatientId().getOrNull()?.toInt() ?: 0
                val info = getWeeklyMoodTrackerInfoUseCase(patientId.toShort())
                _moodTrackerInfoList.value = info.filterNotNull()
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar la informacion del moodTracker"
            } finally {
                _isLoading.value = false
            }
            loadMoodAveragesLabels()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadSleepTrackerInfo() {
        viewModelScope.launch {
            try {
                val patientId = userService.getPatientId().getOrNull()?.toInt() ?: 0
                val info = getWeeklySleepTrackerInfoUseCase(patientId.toShort())
                _sleepTrackerInfoList.value = info
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar la informacion del sleepTracker"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadMoodAveragesLabels() {
        viewModelScope.launch {
            try {
                val patientId = userService.getPatientId().getOrNull()?.toInt() ?: 0
                val averagesLabels = calculateWeeklyMoodAveragesUseCase(patientId.toShort())
                _moodAveragesLabels.value = averagesLabels
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar las etiquetas de los promedios del moodTracker"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun formatDateString(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val displayFormatter = DateTimeFormatter.ofPattern("d/M")
        val date = LocalDate.parse(dateString, formatter)
        return date.format(displayFormatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAverageSleepHours(): Double {
        val average = calculateAverageSleepHoursUseCase(_sleepTrackerInfoList.value)
        return Math.round(average * 10) / 10.0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM", Locale("es", "Arg"))
        return date.format(outputFormatter)
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}