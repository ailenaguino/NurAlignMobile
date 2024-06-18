package com.losrobotines.nuralign.feature_sleep.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_home.domain.usecases.CheckNextTrackerToBeCompletedUseCase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_sleep.domain.usecases.GetSleepTrackerInfoByDateUseCase
import com.losrobotines.nuralign.feature_sleep.domain.usecases.SaveSleepTrackerInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val checkNextTrackerUseCase: CheckNextTrackerToBeCompletedUseCase
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


    fun saveData() {
        if (currentUserExists()) {
            viewModelScope.launch {
                val id = getPatientId()
                val currentDate = getDate()
                saveSleepDataUseCase(
                    id,
                    currentDate,
                    _sliderPosition.value.let { it?.let { it.toInt().toShort() } ?: 0},
                    _bedTime.value ?: "00:00",
                    _negativeThoughts.value ?: false,
                    _anxiousBeforeSleep.value ?: false,
                    _sleptThroughNight.value ?: false,
                    _additionalNotes.value ?: ""
                )
            }
        }
    }


        init {
            //loadMoodTrackerInfo()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun loadMoodTrackerInfo() {
            viewModelScope.launch {
                try {
                    if (currentUserExists()) {
                        val patientId = getPatientId()
                        val date = getDate()
                        val info = getSleepTrackerInfoByDateUseCase(patientId.toInt(), date)
                        if (info != null) {
                            sliderPosition.value = info.sleepHours.toFloat()
                            bedTime.value = info.bedTime
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
                    e.printStackTrace()
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


    private fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
    }

    private suspend fun getPatientId(): Short {
        val idResult: Short
        val uid = authRepository.currentUser!!.uid
        val doc = Firebase.firestore.collection("users").document(uid)
        idResult = doc.get().await().getLong("id")!!.toShort()
        return idResult
    }

    private fun currentUserExists(): Boolean {
        return authRepository.currentUser != null
    }

    fun checkNextTracker() {
        viewModelScope.launch {
            _route.value = checkNextTrackerUseCase(getPatientId().toInt())
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }


}
