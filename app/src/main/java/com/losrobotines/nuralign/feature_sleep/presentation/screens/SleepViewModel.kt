package com.losrobotines.nuralign.feature_sleep.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_home.domain.usecases.CheckNextTrackerToBeCompletedUseCase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import com.losrobotines.nuralign.feature_sleep.domain.usecases.FormatTimeUseCase
import com.losrobotines.nuralign.feature_sleep.domain.usecases.GetSleepDataUseCase
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
    private val getSleepDataUseCase: GetSleepDataUseCase,
    private val formatTimeUseCase: FormatTimeUseCase,
    private val checkNextTrackerUseCase: CheckNextTrackerToBeCompletedUseCase
) :
    ViewModel() {

    private val _route = MutableLiveData("")
    var route: LiveData<String> = _route

    private val _isVisible = MutableLiveData(false)
    var isVisible: LiveData<Boolean> = _isVisible

    private val _isSaved = MutableLiveData(false)
    val isSaved = _isSaved

    private val _sliderPosition = MutableLiveData(0F)
    var sliderPosition: LiveData<Float> = _sliderPosition

    private val _sleepHours = mutableIntStateOf(0)
    val sleepHours = _sleepHours

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

    fun setIsVisible(value: Boolean) {
        _isVisible.value = value
    }

    fun setIsSaved(value: Boolean) {
        _isSaved.value = value
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

    fun setSleepTime(time: Int) {
        _sleepHours.intValue = time
    }

    fun onSliderChanged(sliderValue: Float) {
        _sliderPosition.value = sliderValue
    }

    fun saveData() {
        if (currentUserExists()) {
            viewModelScope.launch {
                _bedTime.value?.let {
                    val formattedBedTime = formatTimeUseCase.removeColonFromTime(_bedTime.value!!)
                    _additionalNotes.value?.let { aditionalNote ->
                        SleepInfo(
                            getPatientId(),
                            getDate(),
                            _sleepHours.intValue.toShort(),
                            formattedBedTime,
                            _negativeThoughts.value.toString()[0].uppercase(),
                            _anxiousBeforeSleep.value.toString()[0].uppercase(),
                            _sleptThroughNight.value.toString()[0].uppercase(),
                            aditionalNote
                        )
                    }
                }?.let {
                    saveSleepDataUseCase.execute(it)
                }
            }
        }
    }


    /*
        init {
            loadMoodTrackerInfoToDatabase()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun loadMoodTrackerInfoToDatabase() {
            viewModelScope.launch {
                try {
                    if (currentUserExists()) {
                        val patientId = getPatentId()
                        val date = getDate()
                        val info = sleepRepository.getSleepData(patientId.toInt())
                        if (info != null) {
                            sleepHours.intValue = info.sleepHours.toInt()
                            bedTime.value = info.bedTime.toString()
                            additionalNotes.value = info.sleepNotes
                            if (info.negativeThoughtsFlag == "F"){
                                setNegativeThoughts(false)
                            } else {
                                setNegativeThoughts(true)
                            }
                            if (info.anxiousFlag == "F"){
                                setAnxiousBeforeSleep(false)
                            } else {
                                setAnxiousBeforeSleep(true)
                            }
                            if(info.sleepStraightFlag=="F"){
                                setSleptThroughNight(false)
                            }else{
                                setSleptThroughNight(true)
                            }
                        } else {
                            isSaved.value = false
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    isSaved.value = false
                }
            }
        }

     */


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

    fun checkNextTracker(){
        viewModelScope.launch {
            _route.value = checkNextTrackerUseCase(getPatientId().toInt())
        }
    }

}
