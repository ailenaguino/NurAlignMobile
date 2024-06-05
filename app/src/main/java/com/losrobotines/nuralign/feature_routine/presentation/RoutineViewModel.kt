package com.losrobotines.nuralign.feature_routine.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.losrobotines.nuralign.feature_routine.data.RoutineRepositoryDatabase
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import com.losrobotines.nuralign.feature_routine.domain.notification.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val routineRepository: RoutineRepositoryDatabase,
    val notification: Notification
) : ViewModel() {

    val selectedDays = mutableStateListOf<String>()

    fun addSelectedDay(day: String) {
        if (!selectedDays.contains(day)) {
            selectedDays.add(day)
        }
    }

    fun removeSelectedDay(day: String) {
        selectedDays.remove(day)
    }

    private val _isSaved = MutableLiveData(false)
    val isSaved: LiveData<Boolean> = _isSaved

    private val _bedTimeRoutine = MutableLiveData("")
    val bedTimeRoutine: LiveData<String> = _bedTimeRoutine

    private val _activity = MutableLiveData("")
    val activity: LiveData<String> = _activity

    private val _activityRoutineTime = MutableLiveData("")
    val activityRoutineTime: LiveData<String> = _activityRoutineTime

    init {
        loadInitialRoutine()
    }

    private fun loadInitialRoutine() {
        val initialRoutine = routineRepository.getRoutine()
        _activityRoutineTime.value = initialRoutine.activityTime
        _bedTimeRoutine.value = initialRoutine.sleepTime
        _activity.value = initialRoutine.activity
        if (_bedTimeRoutine.value != "") {
            setIsSavedRoutine(true)
        }
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

    fun saveRoutine() {
        val routine = RoutineEntity(
            id = 0, // Ajusta según tu lógica de identificación
            sleepTime = _bedTimeRoutine.value ?: "",
            activity = _activity.value ?: "",
            activityTime = _activityRoutineTime.value ?: "00:00"
        )
        routineRepository.addRoutine(routine)
        _isSaved.value = true
    }
}
