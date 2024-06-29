package com.losrobotines.nuralign.feature_therapy.presentation.screens.therapy_session

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo
import com.losrobotines.nuralign.feature_therapy.domain.usecases.therapy_session.EditTherapySessionUseCase
import com.losrobotines.nuralign.feature_therapy.domain.usecases.therapy_session.SaveTherapySessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TherapySessionViewModel @Inject constructor(
    private val userService: UserService,
    private val saveTherapySessionUseCase: SaveTherapySessionUseCase,
    private val editTherapySessionUseCase: EditTherapySessionUseCase
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
    val selectedDate = _selectedDate

    private val _selectedTime = MutableLiveData<String>()
    val selectedTime = _selectedTime

    private val _preSessionNotes = MutableLiveData<String>()
    val preSessionNotes = _preSessionNotes

    private val _postSessionNotes = MutableLiveData<String>()
    val postSessionNotes = _postSessionNotes

    private val _sessionFeel = MutableLiveData<String>()
    val sessionFeel = _sessionFeel

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        getCurrentPatientId()
    }

    fun updateSelectedTherapist(therapist: TherapistInfo) {
        _selectedTherapist.value = therapist
    }

    private fun loadTherapists() {
        viewModelScope.launch {
            val result = userService.getTherapistList(_patientId.value)
            if (result.isSuccess) {
                _therapistList.value = result.getOrNull()!!
            } else {
                _errorMessage.value = "Error al cargar los terapeutas"
            }
        }
    }

    fun updateSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun updateSelectedTime(time: String) {
        _selectedTime.value = time
    }

    private fun formatDate(date: String): String? {
        val originalFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val pattern = Regex("\\d{2}/\\d{2}/\\d{4}")
        if (date.matches(pattern)) {
            val input = originalFormat.parse(date)
            val formattedDate = targetFormat.format(input!!)
            return formattedDate
        } else {
            return date
        }
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
        viewModelScope.launch {
            saveSession()
            val therapySessionInfo = _therapySessionInfo.value!!
            if (therapySessionInfo.id == null) {
                val result = saveTherapySessionUseCase(therapySessionInfo)

                if (result.isSuccess) {
                    _errorMessage.value = "Sesión guardada correctamente"
                    clearSessionState()
                } else {
                    Log.e("TherapySessionViewModel", "Error saving therapy session")
                    _errorMessage.value = "Error al guardar la sesión"
                }
            } else {
                val result = editTherapySessionUseCase(
                    therapySessionInfo.copy(
                        sessionTime = formatTime(_selectedTime.value!!)
                    )
                )

                if (result.isSuccess) {
                    _errorMessage.value = "Sesión editada correctamente"
                    clearSessionState()
                } else {
                    Log.e("TherapySessionViewModel", "Error editing therapy session")
                    _errorMessage.value = "Error al editar la sesión"
                }
            }
        }
    }

    fun loadTherapySessionToEdit(therapySessionInfo: TherapySessionInfo) {
        _therapySessionInfo.value = therapySessionInfo

        if (_therapistList.value != null) {
            _therapistList.value!!.forEach { therapist ->
                if (therapist!!.therapistId == therapySessionInfo.therapistId) {
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

    private fun saveSession() {
        val session = TherapySessionInfo(
            id = _therapySessionInfo.value?.id,
            patientId = _patientId.value,
            therapistId = _selectedTherapist.value?.therapistId!!,
            sessionDate = formatDate(selectedDate.value!!)!!,
            sessionTime = formatTime(selectedTime.value!!),
            preSessionNotes = preSessionNotes.value,
            postSessionNotes = postSessionNotes.value,
            sessionFeel = sessionFeel.value
        )
        _therapySessionInfo.value = session
    }

    private fun clearSessionState() {
        selectedDate.value = ""
        selectedTime.value = ""
        preSessionNotes.value = ""
        postSessionNotes.value = ""
        sessionFeel.value = ""
    }
}