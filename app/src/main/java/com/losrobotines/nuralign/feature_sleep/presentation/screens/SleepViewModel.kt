package com.losrobotines.nuralign.feature_sleep.presentation.screens

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_login.domain.AuthRepository
import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SleepViewModel @Inject constructor(private val sleepRepository: SleepRepository) :
    ViewModel() {

    private val _sliderPosition = MutableLiveData<Float>()
    var sliderPosition: LiveData<Float> = _sliderPosition

    fun onSliderChanged(sliderValue: Float) {
        _sliderPosition.value = sliderValue
    }

    fun retrieveData(hoursSlept: Float) {
        viewModelScope.launch {
            sleepRepository.saveSleepData(
                SleepInfo(
                    1,
                    getDate(),
                    hoursSlept.toInt().toShort(),
                    1,
                    "N",
                    "N",
                    "N",
                    ""
                )
            )
        }
    }

    private fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
    }

}