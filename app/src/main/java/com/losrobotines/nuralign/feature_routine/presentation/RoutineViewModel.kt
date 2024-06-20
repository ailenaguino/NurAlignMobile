package com.losrobotines.nuralign.feature_routine.presentation


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.notification.Notification
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
    val notification: Notification
) : ViewModel() {

    private var _isSaved = MutableLiveData(false)
    var isSaved: LiveData<Boolean> = _isSaved

    private val _bedTimeRoutine = MutableLiveData("")
    val bedTimeRoutine: LiveData<String> = _bedTimeRoutine

    private val _activities = MutableLiveData<List<Activity>>(emptyList())
    val activities: LiveData<List<Activity>> = _activities

    init {
        viewModelScope.launch {
            loadInitialRoutine()
        }
    }

    suspend fun saveRoutine() {
        Log.d("RoutineViewModel", "Saving routine with bedtime: ${_bedTimeRoutine.value} and activities: ${_activities.value}")
        saveRoutineUseCase.invoke(
            bedTimeRoutine = _bedTimeRoutine.value ?: "",
            activities = _activities.value ?: emptyList()
        )
    }

    suspend fun loadInitialRoutine() {
        val initialRoutine = loadRoutineUseCase.invoke()
        _bedTimeRoutine.value = initialRoutine.sleepTime
        _activities.value = initialRoutine.activities
        if (_bedTimeRoutine.value != "") {
            setIsSavedRoutine(true)
        }
        Log.d("RoutineViewModel", "Loaded initial routine: ${_bedTimeRoutine.value} with activities: ${_activities.value}")
    }

    fun setSleepTimeRoutine(time: String) {
        _bedTimeRoutine.value = time
    }

    fun addActivity(activity: Activity) {
        val currentActivities = _activities.value.orEmpty().toMutableList()
        currentActivities.add(activity)
        _activities.value = currentActivities
        Log.d("RoutineViewModel", "Added activity: $activity")
    }

    fun setActivityRoutine(activity: Activity, newTime: String) {
        activity.time = newTime
    }

    fun removeActivity(activity: Activity) {
        val currentActivities = _activities.value.orEmpty().toMutableList()
        currentActivities.remove(activity)
        _activities.value = currentActivities
        Log.d("RoutineViewModel", "Removed activity: $activity")
    }

    fun updateActivityName(activity: Activity, name: String) {
        val currentActivities = _activities.value.orEmpty().toMutableList()
        val index = currentActivities.indexOf(activity)
        if (index >= 0) {
            currentActivities[index] = currentActivities[index].copy(name = name)
            _activities.value = currentActivities
            Log.d("RoutineViewModel", "Updated activity name: $activity")
        }
    }

    fun updateActivityTime(activity: Activity, time: String) {
        val currentActivities = _activities.value.orEmpty().toMutableList()
        val index = currentActivities.indexOf(activity)
        if (index >= 0) {
            currentActivities[index] = currentActivities[index].copy(time = time)
            _activities.value = currentActivities
            Log.d("RoutineViewModel", "Updated activity time: $activity")
        }
    }

    fun addSelectedDayToActivity(activity: Activity, day: String) {
        val currentActivities = _activities.value.orEmpty().toMutableList()
        val index = currentActivities.indexOf(activity)
        if (index >= 0) {
            val updatedDays = currentActivities[index].days.toMutableList().apply { add(day) }
            currentActivities[index] = currentActivities[index].copy(days = updatedDays)
            _activities.value = currentActivities
            Log.d("RoutineViewModel", "Added day to activity: ${activity.name}, days: ${currentActivities[index].days}")
        }
    }

    fun removeSelectedDayFromActivity(activity: Activity, day: String) {
        val currentActivities = _activities.value.orEmpty().toMutableList()
        val index = currentActivities.indexOf(activity)
        if (index >= 0) {
            val updatedDays = currentActivities[index].days.toMutableList().apply { remove(day) }
            currentActivities[index] = currentActivities[index].copy(days = updatedDays)
            _activities.value = currentActivities
            Log.d("RoutineViewModel", "Removed day from activity: ${activity.name}, days: ${currentActivities[index].days}")
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