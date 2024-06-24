package com.losrobotines.nuralign.feature_sleep.presentation.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_home.domain.usecases.CheckNextTrackerToBeCompletedUseCase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import com.losrobotines.nuralign.feature_sleep.domain.usecases.FormatTimeUseCase
import com.losrobotines.nuralign.feature_sleep.domain.usecases.GetSleepTrackerInfoByDateUseCase
import com.losrobotines.nuralign.feature_sleep.domain.usecases.SaveSleepTrackerInfoUseCase
import com.losrobotines.nuralign.feature_sleep.domain.usecases.UpdateSleepTrackerUseCase
import com.losrobotines.nuralign.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SleepViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val saveSleepDataUseCase: SaveSleepTrackerInfoUseCase,
    private val getSleepTrackerInfoByDateUseCase: GetSleepTrackerInfoByDateUseCase,
    private val checkNextTrackerUseCase: CheckNextTrackerToBeCompletedUseCase,
    private val service: UserService,
    private val formatTimeUseCase: FormatTimeUseCase,
    private val updateSleepTrackerUseCase: UpdateSleepTrackerUseCase
) :
    ViewModel() {

    private val _route = MutableLiveData("")
    var route: LiveData<String> = _route

    private val _isVisible = MutableLiveData(false)
    var isVisible: LiveData<Boolean> = _isVisible

    private val _sliderPosition = MutableLiveData(0F)
    var sliderPosition = _sliderPosition

    private val _negativeThoughts = MutableLiveData(false)
    var negativeThoughts = _negativeThoughts

    private val _anxiousBeforeSleep = MutableLiveData(false)
    var anxiousBeforeSleep = _anxiousBeforeSleep

    private val _sleptThroughNight = MutableLiveData(false)
    var sleptThroughNight = _sleptThroughNight

    private val _additionalNotes = MutableLiveData("")
    val additionalNotes = _additionalNotes

    private val _bedTime = MutableLiveData("")
    val bedTime = _bedTime

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isSaved = MutableLiveData(false)
    val isSaved: LiveData<Boolean> = _isSaved

    private val _patientId = mutableStateOf<Short>(0)
    val patientId: State<Short> = _patientId


    fun saveData() {
        if (currentUserExists()) {
            viewModelScope.launch {
                val resultId = service.getPatientId()
                val id = if(resultId.isSuccess) resultId.getOrNull()?:0 else {
                    _errorMessage.value = "Ha habido un error"
                    0
                }
                val currentDate = service.getCurrentDate()
                val trackerExists = getSleepTrackerInfoByDateUseCase(id.toInt(), currentDate)
                if(trackerExists!=null){
                    try{
                        val result = updateSleepTrackerUseCase(id,
                            currentDate,
                            _sliderPosition.value.let { it?.toInt()?.toShort() ?: 0 },
                            _bedTime.value ?: "00:00",
                            _negativeThoughts.value ?: false,
                            _anxiousBeforeSleep.value ?: false,
                            _sleptThroughNight.value ?: false,
                            _additionalNotes.value ?: "")
                        if (result.isSuccess){
                            loadMoodTrackerInfo()
                            _isSaved.value = true

                        }else{
                            _errorMessage.value = "No se pudo guardar la información"
                        }
                    } catch (e: Exception){
                        _errorMessage.value = "Ha habido un error"
                    }
                } else {
                    try {
                        val result = saveSleepDataUseCase(
                            id,
                            currentDate,
                            _sliderPosition.value.let { it?.toInt()?.toShort() ?: 0 },
                            _bedTime.value ?: "00:00",
                            _negativeThoughts.value ?: false,
                            _anxiousBeforeSleep.value ?: false,
                            _sleptThroughNight.value ?: false,
                            _additionalNotes.value ?: ""
                        )
                        if (result.isSuccess){
                            loadMoodTrackerInfo()
                            _isSaved.value = true
                        }else{
                            _errorMessage.value = "No se pudo guardar la información"
                        }
                    } catch (e: Exception){
                        _errorMessage.value = "Ha habido un error"
                    }
                }
            }
        }
    }


        init {
            loadMoodTrackerInfo()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun loadMoodTrackerInfo() {
            viewModelScope.launch {
                try {
                    if (currentUserExists()) {
                        val result = service.getPatientId()
                        val id = if(result.isSuccess) result.getOrNull() ?: 0 else {
                            _errorMessage.value = "Ha habido un error"
                            0
                        }
                        val currentDate = service.getCurrentDate()
                        val info = getSleepTrackerInfoByDateUseCase(id.toInt(), currentDate)
                        if (info != null) {
                            sliderPosition.value = info.sleepHours.toFloat()
                            bedTime.value = formatTimeUseCase.addColonToTime(info.bedTime)
                            additionalNotes.value = info.sleepNotes
                            if (info.negativeThoughtsFlag == "N") {
                                setNegativeThoughts(false)
                            } else {
                                setNegativeThoughts(true)
                            }
                            if (info.anxiousFlag == "N") {
                                setAnxiousBeforeSleep(false)
                            } else {
                                setAnxiousBeforeSleep(true)
                            }
                            if (info.sleepStraightFlag == "N") {
                                setSleptThroughNight(false)
                            } else {
                                setSleptThroughNight(true)
                            }
                        }
                    }
                } catch (e: Exception) {
                    _errorMessage.value = "El usuario no se encuentra logueado"
                }
            }
        }

    fun setIsVisible(value: Boolean) {
        _isVisible.value = value
    }

    fun setSleepTime(time: String) {
        _bedTime.value = time
    }

    fun setAdditionalNotes(notes: String) {
        _additionalNotes.value = notes
    }

    fun setNegativeThoughts(value: Boolean) {
        _negativeThoughts.value = value
    }

    fun setAnxiousBeforeSleep(value: Boolean) {
        _anxiousBeforeSleep.value = value
    }

    fun setSleptThroughNight(value: Boolean) {
        _sleptThroughNight.value = value
    }

    fun onSliderChanged(sliderValue: Float) {
        _sliderPosition.value = sliderValue
    }

    private fun currentUserExists(): Boolean {
        return authRepository.currentUser != null
    }

    fun checkNextTracker() {
        viewModelScope.launch {
            val result = service.getPatientId()
            val id = if(result.isSuccess) result.getOrNull() ?: 0 else {
                _errorMessage.value = "Ha habido un error"
                0
            }
            delay(1000)
            if(_isSaved.value!!){
                _route.value = checkNextTrackerUseCase(id.toInt())
                if (_route.value != Routes.HomeScreen.route) _isVisible.value = true
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
