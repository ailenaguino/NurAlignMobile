package com.losrobotines.nuralign.feature_routine.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_routine.data.RoutineRepositoryDatabase
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import com.losrobotines.nuralign.feature_routine.domain.notification.Notification
import com.losrobotines.nuralign.feature_routine.domain.gemini.GeminiContentGenerator
import com.losrobotines.nuralign.feature_routine.domain.usescases.LoadRoutineUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.SaveRoutineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val loadRoutineUseCase: LoadRoutineUseCase,
    private val saveRoutineUseCase: SaveRoutineUseCase,
    private val geminiContentGenerator: GeminiContentGenerator,
    val notification: Notification
) : ViewModel() {

    val selectedDays = mutableStateListOf<String>()

    private val _isSaved = MutableLiveData(false)
    val isSaved: LiveData<Boolean> = _isSaved

    private val _bedTimeRoutine = MutableLiveData("")
    val bedTimeRoutine: LiveData<String> = _bedTimeRoutine

    private val _activity = MutableLiveData("")
    val activity: LiveData<String> = _activity

    private val _activityRoutineTime = MutableLiveData("")
    val activityRoutineTime: LiveData<String> = _activityRoutineTime

    init {
        viewModelScope.launch {
            loadInitialRoutine()
        }
    }

    suspend fun loadInitialRoutine() {
        val initialRoutine = loadRoutineUseCase.execute()
        _activityRoutineTime.value = initialRoutine.activityTime
        _bedTimeRoutine.value = initialRoutine.sleepTime
        _activity.value = initialRoutine.activity
        selectedDays.addAll(initialRoutine.activityDays)
        if (_bedTimeRoutine.value != "") {
            setIsSavedRoutine(true)
        }
    }

    suspend fun saveRoutine() {
        val routine = RoutineEntity(
            id = 0,
            sleepTime = _bedTimeRoutine.value ?: "",
            activity = _activity.value ?: "",
            activityTime = _activityRoutineTime.value ?: "00:00",
            activityDays = selectedDays.toList() ?: emptyList()

        )
        saveRoutineUseCase.execute(routine)
        _isSaved.value = true
    }

    suspend fun generateNotificationMessage(prompt: String): String? {
        return geminiContentGenerator.generateContent(prompt)

    }

    fun setSleepTimeRoutine(time: String) {
        _bedTimeRoutine.value = time
    }

    fun setActivity(activity: String) {
        _activity.value = activity
    }

    fun setActivityRoutine(activity: String) {
        _activityRoutineTime.value = activity
    }

    fun setIsSavedRoutine(value: Boolean) {
        _isSaved.value = value
    }
    fun addSelectedDay(day: String) {
        if (!selectedDays.contains(day)) {
            selectedDays.add(day)
        }
    }

    fun removeSelectedDay(day: String) {
        selectedDays.remove(day)
    }
}
