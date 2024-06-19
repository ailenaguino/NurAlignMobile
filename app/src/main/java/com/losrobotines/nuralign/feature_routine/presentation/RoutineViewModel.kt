package com.losrobotines.nuralign.feature_routine.presentation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    //  private val updateRoutineUseCase: UpdateRoutineUseCase,
    val notification: Notification
) : ViewModel() {

    private var _isSaved = MutableLiveData(false)
    var isSaved: LiveData<Boolean> = _isSaved

    private val _bedTimeRoutine = MutableLiveData("")
    val bedTimeRoutine: LiveData<String> = _bedTimeRoutine

    private val _activity = MutableLiveData("")
    val activity: LiveData<String> = _activity


    private val _activityRoutineTime = MutableLiveData("")
    val activityRoutineTime: LiveData<String> = _activityRoutineTime

    val selectedDays = mutableStateListOf<String>()

    private val _activity2 = MutableLiveData("")
    val activity2: LiveData<String> = _activity2


    private val _activityRoutineTime2 = MutableLiveData("")
    val activityRoutineTime2: LiveData<String> = _activityRoutineTime2

    val selectedDays2 = mutableStateListOf<String>()


    init {
        viewModelScope.launch {
            loadInitialRoutine()
        }
    }

    suspend fun saveRoutine() {
        saveRoutineUseCase.invoke(
            _bedTimeRoutine.value ?: "",
            _activity.value ?: "",
            _activityRoutineTime.value ?: "",
            selectedDays.toList(),
            _activity2.value ?: "",
            _activityRoutineTime2.value ?: "",
            selectedDays2.toList()
        )
    }


    suspend fun loadInitialRoutine() {
        val initialRoutine = loadRoutineUseCase.invoke()
        _activityRoutineTime.value = initialRoutine.activityTime
        _bedTimeRoutine.value = initialRoutine.sleepTime
        _activity.value = initialRoutine.activity
        selectedDays.addAll(initialRoutine.activityDays)
        _activityRoutineTime2.value = initialRoutine.activityTime2
        _activity2.value = initialRoutine.activity2
        selectedDays2.addAll(initialRoutine.activityDays2)
        if (_bedTimeRoutine.value != "") {
            setIsSavedRoutine(true)
        }
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
    fun setActivity2(activity: String) {
        _activity2.value = activity
    }

    fun setActivityRoutine2(activity: String) {
        _activityRoutineTime2.value = activity
    }

    fun removeSelectedDay2(day: String) {
        selectedDays2.remove(day)
    }

    fun addSelectedDay2(day: String) {
        if (!selectedDays2.contains(day)) {
            selectedDays2.add(day)
        }
    }


    fun clearAll() {
        _activity.value = ""
        _activityRoutineTime.value = ""
        selectedDays.clear()
        _isSaved.value = false
    }
    fun clearAllSecondActivity() {
        _activity2.value = ""
        _activityRoutineTime2.value = ""
        selectedDays2.clear()
        _isSaved.value = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseTime(time: String): LocalTime? {
        val parts = time.split(":")
        return LocalTime.of(parts[0].toInt(), parts[1].toInt())
    }


}