package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MoodTrackerViewModel @Inject constructor(
    private val moodTrackerRepository: MoodTrackerRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val isSaved = MutableLiveData(false)

    val effectiveDate = MutableLiveData<LocalDate>()


    val highestValue = mutableIntStateOf(-1)
    val highestNote = mutableStateOf("")

    val lowestValue = mutableIntStateOf(-1)
    val lowestNote = mutableStateOf("")

    val irritableValue = mutableIntStateOf(-1)
    val irritableNote = mutableStateOf("")

    val anxiousValue = mutableIntStateOf(-1)
    val anxiousNote = mutableStateOf("")
/*
    init {
        // Load the data when ViewModel is created
        loadMoodTrackerInfo()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadMoodTrackerInfo() {
        viewModelScope.launch {
            if (currentUserExists()) {
                val patientId = 8 // Modificado a String
                val date = getDate()
                val info = moodTrackerRepository.getMoodTrackerInfo(patientId, date)
                info?.let {
                    highestValue.intValue = it.highestValue.toInt()
                    highestNote.value = it.highestNote
                    lowestValue.intValue = it.lowestValue.toInt()
                    lowestNote.value = it.lowestNote
                    irritableValue.intValue = it.irritableValue.toInt()
                    irritableNote.value = it.irritableNote
                    anxiousValue.intValue = it.anxiousValue.toInt()
                    anxiousNote.value = it.anxiousNote
                    effectiveDate.value = LocalDate.parse(it.effectiveDate)
                    isSaved.value = true
                }
            }
        }
    }

 */


    fun saveData() {
        //  if (currentUserExists()) {
        //    getPatentId()
        viewModelScope.launch {
            moodTrackerRepository.saveMoodTrackerInfo(
                MoodTrackerInfo(
                    patientId = 8,
                    effectiveDate = getDate(),
                    highestValue = highestValue.intValue.toString()  /*.absoluteValue  SI AGREGO ESTO ME SALE ERROR 500*/,
                    lowestValue = lowestValue.intValue.toString()/*.absoluteValue*/,
                    highestNote = highestNote.value,
                    lowestNote = lowestNote.value,
                    irritableValue = irritableValue.intValue.toString()/*.absoluteValue*/,
                    irritableNote = irritableNote.value,
                    anxiousValue = anxiousValue.intValue.toString() /*.absoluteValue*/,
                    anxiousNote = anxiousNote.value
                )
            )
        }
        //}
    }


    private fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
    }


    private fun getPatentId() {
        val uid = authRepository.currentUser!!.uid
        val doc = Firebase.firestore.collection("users").document(uid)
        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Firestore", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
            }

    }

    private fun currentUserExists(): Boolean {
        return authRepository.currentUser != null
    }

}



