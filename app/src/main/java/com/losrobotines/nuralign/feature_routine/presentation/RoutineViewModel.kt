package com.losrobotines.nuralign.feature_routine.presentation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.notification.Notification
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityProviderUseCase
import com.losrobotines.nuralign.gemini.GeminiContentGenerator
import com.losrobotines.nuralign.feature_routine.domain.usescases.LoadRoutineUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.SaveRoutineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val loadRoutineUseCase: LoadRoutineUseCase,
    private val saveRoutineUseCase: SaveRoutineUseCase,
    private val geminiContentGenerator: GeminiContentGenerator,
    private val activityProviderUseCase: ActivityProviderUseCase,
    val notification: Notification
) : ViewModel() {

    private var _isSaved = MutableLiveData(false)
    var isSaved: LiveData<Boolean> = _isSaved

    private val _bedTimeRoutine = MutableLiveData<String>()
    val bedTimeRoutine: LiveData<String> = _bedTimeRoutine

    private val _activities = MutableLiveData<List<Activity>>(emptyList())
    val activities: LiveData<List<Activity>> = _activities

    init {
        viewModelScope.launch {
            loadInitialRoutine()
        }
    }

    suspend fun saveRoutine() {
        saveRoutineUseCase.invoke(
            bedTimeRoutine = _bedTimeRoutine.value ?: "",
            activities = _activities.value ?: emptyList()
        )
    }

    suspend fun loadInitialRoutine() {
        val initialRoutine = loadRoutineUseCase.invoke()
        _bedTimeRoutine.value = initialRoutine.sleepTime
        _activities.value = initialRoutine.activities
        if (_bedTimeRoutine.value != null) {
            setIsSavedRoutine(true)
        }
    }

    fun setSleepTimeRoutine(time: String) {
        _bedTimeRoutine.value = time
    }

    fun addActivity(activity: Activity) {
        viewModelScope.launch {
            activityProviderUseCase.addActivity(activity)
            _activities.value =
                _activities.value?.plus(activity)
        }
    }

    fun setActivityRoutine(activity: Activity, newTime: String) {
        viewModelScope.launch {
            activityProviderUseCase.updateActivityTime(activity, newTime)
        }
    }

    fun removeActivity(activity: Activity) {
        viewModelScope.launch {
            activityProviderUseCase.removeActivity(activity)
            _activities.value =
                _activities.value.orEmpty().toMutableList().apply { remove(activity) }
        }
    }

    fun updateActivityTime(activity: Activity, newTime: String) {
        viewModelScope.launch {
            activityProviderUseCase.updateActivityTime(activity, newTime)
            val currentActivities = _activities.value.orEmpty().toMutableList()
            val index = currentActivities.indexOf(activity)
            if (index >= 0) {
                currentActivities[index] = currentActivities[index].copy(time = newTime)
                _activities.value = currentActivities
            }
        }
    }

    fun updateActivityName(activity: Activity, name: String) {
        viewModelScope.launch {
            activityProviderUseCase.updateActivityName(activity, name)
            val currentActivities = _activities.value.orEmpty().toMutableList()
            val index = currentActivities.indexOf(activity)
            if (index >= 0) {
                currentActivities[index] = currentActivities[index].copy(name = name)
                _activities.value = currentActivities
            }
        }
    }

    fun addSelectedDayToActivity(activity: Activity, day: String) {
        viewModelScope.launch {
            activityProviderUseCase.addSelectedDayToActivity(activity, day)
            val currentActivities = _activities.value.orEmpty().toMutableList()
            val index = currentActivities.indexOf(activity)
            if (index >= 0) {
                val updatedDays = currentActivities[index].days.toMutableList().apply { add(day) }
                currentActivities[index] = currentActivities[index].copy(days = updatedDays)
                _activities.value = currentActivities
            }
        }
    }

    fun removeSelectedDayFromActivity(activity: Activity, day: String) {
        viewModelScope.launch {
            activityProviderUseCase.removeSelectedDayFromActivity(activity, day)
            val currentActivities = _activities.value.orEmpty().toMutableList()
            val index = currentActivities.indexOf(activity)
            if (index >= 0) {
                val updatedDays =
                    currentActivities[index].days.toMutableList().apply { remove(day) }
                currentActivities[index] = currentActivities[index].copy(days = updatedDays)
                _activities.value = currentActivities
            }
        }
    }

    fun setIsSavedRoutine(value: Boolean) {
        _isSaved.value = value
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseTime(time: String): LocalTime? {
        val parts = time.split(":")
        return LocalTime.of(parts[0].toInt(), parts[1].toInt())
    }

    suspend fun generateNotificationMessage(prompt: String): String? {
        return geminiContentGenerator.generateContent(prompt)
    }
}