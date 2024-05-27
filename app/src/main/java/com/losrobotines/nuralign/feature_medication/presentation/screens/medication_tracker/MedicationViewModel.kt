package com.losrobotines.nuralign.feature_medication.presentation.screens.medication_tracker

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val medicationRepository: MedicationRepository
) : ViewModel() {
    val medicationList = mutableListOf<MedicationInfo>()
    val medicationName = mutableStateOf("viewmodel")
    val medicationGrammage = mutableIntStateOf(9)
    val medicationOptionalFlag = mutableStateOf("viewmodel")

    init {
        loadMedicationInfoFromDatabase()
    }

    private fun loadMedicationInfoFromDatabase() {
        viewModelScope.launch {
            try {
                if (currentUserExists()) {
                    val patientId =
                        2.toShort() //una vez que esté el GET con patient_id, reemplazar por getPatientId()
                    val info = medicationRepository.getMedicationList(patientId)
                    if (info.isNotEmpty()) {
                        for (med in info) {
                            medicationList.add(
                                MedicationInfo(
                                    patientId,
                                    med!!.medicationName,
                                    med.medicationGrammage,
                                    med.medicationOptionalFlag
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveData() {
        viewModelScope.launch {
            medicationRepository.saveMedicationInfo(medicationList)
        }
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

    private fun addNewMedicationToList(medication: MedicationInfo) {
        medicationList.add(
            MedicationInfo(
                medication.patientId,
                medication.medicationName,
                medication.medicationGrammage,
                medication.medicationOptionalFlag
            )
        )

        Log.d(
            "addNewMedicationToList",
            "medicación: ${medicationName.value} ${medicationGrammage.intValue}mg agregado al paciente ${medication.patientId}"
        )
    }
}