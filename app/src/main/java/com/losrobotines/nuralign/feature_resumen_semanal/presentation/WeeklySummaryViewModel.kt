package com.losrobotines.nuralign.feature_resumen_semanal.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_resumen_semanal.domain.usecases.CalculateAverageBedTimeUseCase
import com.losrobotines.nuralign.feature_resumen_semanal.domain.usecases.CalculateAverageSleepHoursUseCase
import com.losrobotines.nuralign.feature_resumen_semanal.domain.usecases.GetWeeklyMoodTrackerInfoUseCase
import com.losrobotines.nuralign.feature_resumen_semanal.domain.usecases.GetWeeklySleepTrackerInfoUseCase
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@SuppressLint("NewApi")
@HiltViewModel
class WeeklySummaryViewModel @Inject constructor(
    private val userService: UserService,
    private val getWeeklyMoodTrackerInfoUseCase: GetWeeklyMoodTrackerInfoUseCase,
    private val getWeeklySleepTrackerInfoUseCase: GetWeeklySleepTrackerInfoUseCase,
    private val calculateAverageSleepHoursUseCase: CalculateAverageSleepHoursUseCase,
    private val calculateAverageBedTimeUseCase: CalculateAverageBedTimeUseCase
) : ViewModel() {

    private val _moodTrackerInfoList = MutableStateFlow<List<MoodTrackerInfo?>>(emptyList())
    val moodTrackerInfoList: StateFlow<List<MoodTrackerInfo?>> get() = _moodTrackerInfoList

    private val _sleepTrackerInfoList = MutableStateFlow<List<SleepInfo?>>(emptyList())
    val sleepTrackerInfoList: StateFlow<List<SleepInfo?>> get() = _sleepTrackerInfoList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        loadMoodTrackerInfo()
        loadSleepTrackerInfo()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadMoodTrackerInfo() {
        viewModelScope.launch {
            try {
                val patientId = userService.getPatientId().getOrNull()?.toInt() ?: 0
                val info = getWeeklyMoodTrackerInfoUseCase(patientId.toShort())

                _moodTrackerInfoList.value = info
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
    fun getAverageBedTime(): String {
        return calculateAverageBedTimeUseCase(_sleepTrackerInfoList.value)
    }


}