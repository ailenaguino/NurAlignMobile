package com.losrobotines.nuralign.feature_sleep.presentation.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SleepViewModel @Inject constructor(
    private val sleepRepository: SleepRepository,
    private val authRepository: AuthRepository
) :
    ViewModel() {

    private val _sliderPosition = MutableLiveData(0F)
    var sliderPosition: LiveData<Float> = _sliderPosition

    fun onSliderChanged(sliderValue: Float) {
        _sliderPosition.value = sliderValue
    }

    fun saveData() {
        if (currentUserExists()) {
            viewModelScope.launch {
                sleepRepository.saveSleepData(
                    SleepInfo(
                        getPatentId(),
                        getDate(),
                        _sliderPosition.value!!.toInt().toShort(),
                        1,
                        "N",
                        "N",
                        "N",
                        ""
                    )
                )
            }
        }
    }



    private fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
    }

    private suspend fun getPatentId(): Short {
        val idResult: Short
        val uid = authRepository.currentUser!!.uid
        val doc = Firebase.firestore.collection("users").document(uid)
        idResult = doc.get().await().getLong("id")!!.toShort()
        return idResult
    }

    private fun currentUserExists(): Boolean {
        return authRepository.currentUser != null
    }

}