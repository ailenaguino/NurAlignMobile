package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation

import android.content.Context
import android.os.Build
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_achievements.domain.usecases.TrackerIsSavedUseCase
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsViewModel
import com.losrobotines.nuralign.feature_companion.presentation.screens.CompanionViewModel
import com.losrobotines.nuralign.feature_home.domain.usecases.CheckNextTrackerToBeCompletedUseCase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases.GetMoodTrackerInfoByDateUseCase
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases.SaveMoodTrackerDataUseCase
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases.UpdateMoodTrackerUseCase
import com.losrobotines.nuralign.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MoodTrackerViewModel @Inject constructor(
    private val saveMoodTrackerDataUseCase: SaveMoodTrackerDataUseCase,
    private val getMoodTrackerInfoByDateUseCase: GetMoodTrackerInfoByDateUseCase,
    private val authRepository: AuthRepository,
    private val service: UserService,
    private val checkNextTrackerToBeCompletedUseCase: CheckNextTrackerToBeCompletedUseCase,
    private val updateMoodTrackerUseCase: UpdateMoodTrackerUseCase,
    private val trackerIsSavedUseCase: TrackerIsSavedUseCase
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
                    val result = service.getPatientId()
                    val id = if (result.isSuccess) result.getOrNull() ?: 0 else {
                        _errorMessage.value = "Ha habido un error"
                        0
                    }
                    val currentDate = service.getCurrentDate()
                    val info = getMoodTrackerInfoByDateUseCase(id.toInt(), currentDate)
                    if (info != null) {
                        highestValue.intValue = info.highestValue.toInt()
                        highestNote.value = info.highestNote
                        lowestValue.intValue = info.lowestValue.toInt()
                        lowestNote.value = info.lowestNote
                        irritableValue.intValue = info.irritableValue.toInt()
                        irritableNote.value = info.irritableNote
                        anxiousValue.intValue = info.anxiousValue.toInt()
                        anxiousNote.value = info.anxiousNote
                        effectiveDate.value = LocalDate.parse(info.effectiveDate)
                    }
                } else {
                    resetData()
                    _errorMessage.value = "El usuario no se encuentra logueado"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Ha habido un error"
            }
        }
    }


    fun saveData(context: Context) {
        viewModelScope.launch {
            val resultId = service.getPatientId()
            val id = if (resultId.isSuccess) resultId.getOrNull() ?: 0 else {
                _errorMessage.value = "Ha habido un error"
                0
            }
            val currentDate = service.getCurrentDate()
            val trackerExists = getMoodTrackerInfoByDateUseCase(id.toInt(), currentDate)
            if (trackerExists == null) {
                try {
                    val result = saveMoodTrackerDataUseCase(
                        MoodTrackerInfo(
                            patientId = id,
                            effectiveDate = currentDate,
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
                    if (result.isSuccess) {
                        loadMoodTrackerInfo()
                        _isSaved.value = true
                        trackerIsSavedUseCase(context, AchievementsViewModel.TrackerConstants.MOOD_TRACKER)

                    } else {
                        _errorMessage.value = "No se pudo guardar la información"
                    }
                } catch (e: Exception) {
                    _errorMessage.value = "Ha habido un error"
                }
            } else {
                try {
                    val result = updateMoodTrackerUseCase(
                        MoodTrackerInfo(
                            patientId = id,
                            effectiveDate = currentDate,
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
                    if (result.isSuccess) {
                        loadMoodTrackerInfo()
                        _isSaved.value = true

                    } else {
                        _errorMessage.value = "No se pudo guardar la información"
                    }
                } catch (e: Exception) {
                    _errorMessage.value = "Ha habido un error"
                }
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

    private fun currentUserExists(): Boolean {
        return authRepository.currentUser != null
    }

    fun checkNextTracker() {
        viewModelScope.launch {
            val result = service.getPatientId()
            val id = if (result.isSuccess) result.getOrNull() ?: 0 else {
                _errorMessage.value = "Ha habido un error"
                0
            }
            delay(1000)
            if (_isSaved.value!!) {
                _route.value = checkNextTrackerToBeCompletedUseCase(id.toInt())
                if (_route.value != Routes.HomeScreen.route) _isVisible.value =
                    true else _errorMessage.value = "Ya completaste todos los seguimientos!"
            }
        }
    }

    fun setIsVisible(value: Boolean) {
        _isVisible.value = value
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}