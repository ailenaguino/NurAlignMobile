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
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.CalculateAverageSleepHoursUseCase
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.CalculateWeeklyMoodAveragesUseCase
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.GetWeeklyMedicationTrackerInfoUseCase
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.GetWeeklyMoodTrackerInfoUseCase
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.GetWeeklySleepTrackerInfoUseCase
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.MoodTrackerAveragesLabels
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@SuppressLint("NewApi")
@HiltViewModel
class WeeklySummaryViewModel @Inject constructor(
    private val userService: UserService,
    private val getWeeklyMoodTrackerInfoUseCase: GetWeeklyMoodTrackerInfoUseCase,
    private val getWeeklySleepTrackerInfoUseCase: GetWeeklySleepTrackerInfoUseCase,
    private val getWeeklyMedicationTrackerInfoUseCase: GetWeeklyMedicationTrackerInfoUseCase,
    private val calculateAverageSleepHoursUseCase: CalculateAverageSleepHoursUseCase,
    private val calculateWeeklyMoodAveragesUseCase: CalculateWeeklyMoodAveragesUseCase
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

    init {
        loadMoodTrackerInfo()
        loadSleepTrackerInfo()
        loadMedicationTrackerInfo()
        loadMoodAveragesLabels()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadMedicationTrackerInfo() {
        viewModelScope.launch {
            try {
                val patientId = userService.getPatientId().getOrNull()?.toInt() ?: 0
                val info = getWeeklyMedicationTrackerInfoUseCase(patientId.toShort())
                Log.d("MedicationTrackerInfo", info.toString())

                _medicationTrackerInfoList.value = info
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

                // Filtrar la lista para omitir el día más reciente si no está completo
                _moodTrackerInfoList.value = info.filterNotNull()
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar la informacion del moodTracker"
            } finally {
                _isLoading.value = false
            }
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
        return calculateAverageSleepHoursUseCase(_sleepTrackerInfoList.value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM")
        return date.format(outputFormatter)
    }






}