package com.losrobotines.nuralign.feature_therapy.presentation.screens.therapy_session

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo
import com.losrobotines.nuralign.feature_therapy.presentation.screens.therapists.TherapistViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TherapySessionViewModel @Inject constructor(
    private val userService: UserService
) : ViewModel() {
    private val _patientId = mutableStateOf<Short>(0)
    val patientId: State<Short> = _patientId

    private val _therapistList = MutableLiveData<List<TherapistInfo?>>()
    var therapistList: LiveData<List<TherapistInfo?>> = _therapistList

    private val _selectedTherapist = MutableLiveData<TherapistInfo>()
    val selectedTherapist: LiveData<TherapistInfo> = _selectedTherapist

    private val _therapySessionInfo = MutableLiveData<TherapySessionInfo>()
    val therapySessionInfo: LiveData<TherapySessionInfo> = _therapySessionInfo

    private val _selectedDate = MutableLiveData<String>()
    var selectedDate: LiveData<String> = _selectedDate

    private val _selectedTime = MutableLiveData<String>()
    var selectedTime: LiveData<String> = _selectedTime

    private val _preSessionNotes = MutableLiveData<String>()
    val preSessionNotes: LiveData<String> = _preSessionNotes

    private val _postSessionNotes = MutableLiveData<String>()
    val postSessionNotes: LiveData<String> = _postSessionNotes

    private val _sessionFeel = MutableLiveData<String>()
    val sessionFeel: LiveData<String> = _sessionFeel

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        getCurrentPatientId()
    }

    fun updateSelectedTherapist(therapist: TherapistInfo) {
        _selectedTherapist.value = therapist
    }

    private fun loadTherapists() {
        val newList = listOf(
            TherapistInfo(2, "William", "Scottman", "wscottman@gmail.com", 1112344321, "N"),
            TherapistInfo(3, "Bob", "Smith", "bsmith@gmail.com", 1132143214, "N")
        )
        _therapistList.value = newList
        //_therapistList.value = therapistViewModel.therapistList.value
    }

    fun updateSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun updateSelectedTime(time: String) {
        _selectedTime.value = time
    }

    private fun formatDate(date: String): String {
        val originalFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val input = originalFormat.parse(date)
        val formattedDate = targetFormat.format(input!!)

        return formattedDate
    }

    private fun formatTime(time: String): Short {
        val formattedTime = time.replace(":", "")

        return formattedTime.toShort()
    }

    fun addColonTime(time: Short): String {
        val hours = time / 100
        val minutes = time % 100

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
        }

        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun updatePreSessionNotes(notes: String) {
        _preSessionNotes.value = notes
    }

    fun updatePostSessionNotes(notes: String) {
        _postSessionNotes.value = notes
    }

    fun updateSessionFeel(feel: String) {
        _sessionFeel.value = feel
    }

    private fun getCurrentPatientId() {
        viewModelScope.launch {
            val result = userService.getPatientId()

            if (result.isSuccess) {
                _patientId.value = result.getOrNull()!!
                loadTherapists()
            } else {
                Log.e(
                    "TherapistViewModel",
                    "Error getting patient ID: ${result.exceptionOrNull()?.message}"
                )
                _errorMessage.value = "Error al obtener el ID del paciente"
            }
        }
    }

    fun saveTherapySession() {
        _therapySessionInfo.value = TherapySessionInfo(
            patientId = _patientId.value,
            therapistId = _selectedTherapist.value!!.id!!,
            sessionDate = formatDate(_selectedDate.value!!),
            sessionTime = formatTime(_selectedTime.value!!),
            preSessionNotes = _preSessionNotes.value,
            postSessionNotes = _postSessionNotes.value,
            sessionFeel = _sessionFeel.value
        )
    }

    fun loadTherapySessionToEdit(therapySessionInfo: TherapySessionInfo) {
        _therapySessionInfo.value = therapySessionInfo

        if (_therapistList.value != null) {
            _therapistList.value!!.forEach { therapist ->
                if (therapist!!.id == therapySessionInfo.therapistId) {
                    therapist.let {
                        _selectedTherapist.value = it
                    }
                }
            }
        }

        _selectedDate.value = therapySessionInfo.sessionDate
        _selectedTime.value = addColonTime(therapySessionInfo.sessionTime)
        _preSessionNotes.value = therapySessionInfo.preSessionNotes ?: ""
        _postSessionNotes.value = therapySessionInfo.postSessionNotes ?: ""
        _sessionFeel.value = therapySessionInfo.sessionFeel ?: ""
    }

    fun checkLogs() {
        Log.d("TherapySessionViewModel", "therapistList ${_therapistList.value}")
        Log.d("TherapySessionViewModel", "selectedTherapist ${_selectedTherapist.value}")
        Log.d("TherapySessionViewModel", "selectedDate ${formatDate(_selectedDate.value!!)}")
        Log.d("TherapySessionViewModel", "selectedTime ${formatTime(_selectedTime.value!!)}")
        Log.d("TherapySessionViewModel", "preSessionNotes ${_preSessionNotes.value}")
        Log.d("TherapySessionViewModel", "postSessionNotes ${_postSessionNotes.value}")
        Log.d("TherapySessionViewModel", "sessionFeel ${_sessionFeel.value}")
    }
}