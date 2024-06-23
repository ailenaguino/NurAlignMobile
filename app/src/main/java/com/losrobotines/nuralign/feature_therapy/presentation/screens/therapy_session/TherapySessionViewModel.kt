package com.losrobotines.nuralign.feature_therapy.presentation.screens.therapy_session

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TherapySessionViewModel @Inject constructor(
    private val userService: UserService
) : ViewModel() {
    private val _therapistList = MutableLiveData<List<TherapistInfo>>()
    val therapistList: LiveData<List<TherapistInfo>> = _therapistList

    private val _selectedTherapist = MutableLiveData<TherapistInfo>()
    val selectedTherapist: LiveData<TherapistInfo> = _selectedTherapist

    private val _selectedDate = MutableLiveData<String>()
    var selectedDate: LiveData<String> = _selectedDate

    private val _selectedTime = MutableLiveData<String>()
    var selectedTime: LiveData<String> = _selectedTime

    private val _preSessionNotes = MutableLiveData<String>()
    val preSessionNotes: LiveData<String> = _preSessionNotes

    private val _postSessionNotes = MutableLiveData<String>()
    val postSessionNotes: LiveData<String> = _postSessionNotes

    private val _sessionFeel = MutableLiveData<Int>()
    val sessionFeel: LiveData<Int> = _sessionFeel

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        Log.d("TherapySessionViewModel", "init ${_therapistList.value}")
        loadTestTherapists()
        Log.d("TherapySessionViewModel", "load ${_therapistList.value}")
    }

    fun selectTherapist(therapist: TherapistInfo) {
        _selectedTherapist.value = therapist
    }

    private fun loadTestTherapists() {
        val newList = (_therapistList.value ?: emptyList()).toMutableList()
        newList.addAll(
            listOf(
                TherapistInfo(1, "William", "Wattford", "william@email.com", 1234567431, "N"),
                TherapistInfo(2, "Bob", "Smith", "bob@email.com", 1234567890, "N")
            )
        )
        _therapistList.value = newList
    }

    fun updateSelectedDate(date: String) {
        _selectedDate.value = date
        Log.d("TherapySessionViewModel", "updateSelectedDate ${_selectedDate.value}")

    }

    fun updateSelectedTime(time: String) {
        _selectedTime.value = time
        Log.d("TherapySessionViewModel", "updateSelectedTime ${_selectedTime.value}")
    }
}