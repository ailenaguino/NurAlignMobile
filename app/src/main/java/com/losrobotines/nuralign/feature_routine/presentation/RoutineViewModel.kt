package com.losrobotines.nuralign.feature_routine.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.losrobotines.nuralign.notification.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class RoutineViewModel @Inject constructor(
    val notification: Notification
) : ViewModel() {

    private val _isSaved = MutableLiveData(false)
    val isSaved: LiveData<Boolean> = _isSaved

    private val _bedTimeRoutine = MutableLiveData("")
    val bedTimeRoutine: LiveData<String> = _bedTimeRoutine

    fun setSleepTimeRoutine(time: String) {
        _bedTimeRoutine.value = time
    }

    fun setIsSavedRoutine(value: Boolean) {
        _isSaved.value = value
    }

    // Agrega métodos relacionados con Room aquí si es necesario
}