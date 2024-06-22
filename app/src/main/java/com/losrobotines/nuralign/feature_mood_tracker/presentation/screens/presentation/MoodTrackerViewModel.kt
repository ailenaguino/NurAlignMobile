package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_home.domain.usecases.CheckNextTrackerToBeCompletedUseCase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases.GetMoodTrackerInfoByDateUseCase
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases.SaveMoodTrackerDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MoodTrackerViewModel @Inject constructor(
    private val saveMoodTrackerDataUseCase: SaveMoodTrackerDataUseCase,
    private val getMoodTrackerInfoByDateUseCase: GetMoodTrackerInfoByDateUseCase,
    private val authRepository: AuthRepository,
    private val checkNextTrackerToBeCompletedUseCase: CheckNextTrackerToBeCompletedUseCase
) : ViewModel() {
    private val _isSaved = MutableLiveData(false)
    var isSaved: LiveData<Boolean> = _isSaved

    private val _route = MutableLiveData("")
    var route: LiveData<String> = _route

    private val _isVisible = MutableLiveData(false)
    var isVisible: LiveData<Boolean> = _isVisible

    val effectiveDate = MutableLiveData<LocalDate?>()

    val highestValue = mutableIntStateOf(-1)
    val highestNote = mutableStateOf("")

    val lowestValue = mutableIntStateOf(-1)
    val lowestNote = mutableStateOf("")

    val irritableValue = mutableIntStateOf(-1)
    val irritableNote = mutableStateOf("")

    val anxiousValue = mutableIntStateOf(-1)
    val anxiousNote = mutableStateOf("")

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        loadMoodTrackerInfo()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadMoodTrackerInfo() {
        viewModelScope.launch {
            try {
                if (currentUserExists()) {
                    val patientId = patientId()
                    val currentDate = getCurrentDate()
                    val info = getMoodTrackerInfoByDateUseCase(patientId.toInt(), currentDate)

                    Log.d("MoodTrackerViewModel", "Fecha actual: $currentDate")
                    Log.d("MoodTrackerViewModel", "Datos obtenidos: $info")

                    if (info != null && info.effectiveDate == currentDate) {
                        highestValue.intValue = info.highestValue.toInt()
                        highestNote.value = info.highestNote
                        lowestValue.intValue = info.lowestValue.toInt()
                        lowestNote.value = info.lowestNote
                        irritableValue.intValue = info.irritableValue.toInt()
                        irritableNote.value = info.irritableNote
                        anxiousValue.intValue = info.anxiousValue.toInt()
                        anxiousNote.value = info.anxiousNote
                        effectiveDate.value = LocalDate.parse(info.effectiveDate)
                        _isSaved.value = true
                        Log.d("MoodTrackerViewModel", "Datos cargados correctamente")
                    } else {
                        resetData()
                        Log.d("MoodTrackerViewModel", "No hay datos para la fecha actual, restableciendo datos")
                    }
                } else {
                    resetData()
                    Log.d("MoodTrackerViewModel", "Usuario no encontrado, restableciendo datos")
                }
            } catch (e: Exception) {
                Log.e("MoodTrackerViewModel", "Error loading mood tracker info: ${e.message}")
                _errorMessage.value = "Error al cargar el seguimiento del estado de ánimo"
            }
        }
    }


    fun saveData() {
        viewModelScope.launch {
            try {
                saveMoodTrackerDataUseCase.invoke(
                    MoodTrackerInfo(
                        patientId = patientId(),
                        effectiveDate = getCurrentDate(),
                        highestValue = highestValue.intValue.toString(),
                        lowestValue = lowestValue.intValue.toString(),
                        highestNote = highestNote.value,
                        lowestNote = lowestNote.value,
                        irritableValue = irritableValue.intValue.toString(),
                        irritableNote = irritableNote.value,
                        anxiousValue = anxiousValue.intValue.toString(),
                        anxiousNote = anxiousNote.value
                    )
                )
                _isSaved.value = true
                Log.d("MoodTrackerViewModel", "Datos guardados correctamente")
            } catch (e: Exception) {
                Log.e("MoodTrackerViewModel", "Error saving mood tracker info: ${e.message}")
                _errorMessage.value = "Error al guardar el seguimiento del estado de ánimo"
            }
        }
    }
    private fun resetData() {
        highestValue.intValue = -1
        highestNote.value = ""
        lowestValue.intValue = -1
        lowestNote.value = ""
        irritableValue.intValue = -1
        irritableNote.value = ""
        anxiousValue.intValue = -1
        anxiousNote.value = ""
        effectiveDate.value = null
        _isSaved.value = false
    }
    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date())
    }

    private suspend fun patientId(): Short {
        val uid = authRepository.currentUser!!.uid
        val doc = Firebase.firestore.collection("users").document(uid).get().await()
        return doc.getLong("id")!!.toShort()
    }

    private fun currentUserExists(): Boolean {
        return authRepository.currentUser != null
    }

    fun checkNextTracker() {
        viewModelScope.launch {
            _route.value = checkNextTrackerToBeCompletedUseCase(patientId().toInt())
        }
    }

    fun setIsVisible(value: Boolean) {
        _isVisible.value = value
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}