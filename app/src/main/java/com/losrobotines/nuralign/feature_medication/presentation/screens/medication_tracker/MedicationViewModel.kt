package com.losrobotines.nuralign.feature_medication.presentation.screens.medication_tracker

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.feature_home.domain.usecases.GetPatientIdUseCase
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.usecases.EditExistingMedicationInListUseCase
import com.losrobotines.nuralign.feature_medication.domain.usecases.GetMedicationInfoFromDatabaseUseCase
import com.losrobotines.nuralign.feature_medication.domain.usecases.PrepareMedicationDataToSaveItIntoDatabaseUseCase
import com.losrobotines.nuralign.feature_medication.domain.usecases.SaveMedicationInfoToDatabaseUseCase
import com.losrobotines.nuralign.feature_medication.domain.usecases.SaveMedicationInfoToMedicationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val getPatientIdUseCase: GetPatientIdUseCase,
    private val getMedicationInfoFromDatabaseUseCase: GetMedicationInfoFromDatabaseUseCase,
    private val saveMedicationInfoToDatabaseUseCase: SaveMedicationInfoToDatabaseUseCase,
    private val prepareMedicationDataToSaveItIntoDatabaseUseCase: PrepareMedicationDataToSaveItIntoDatabaseUseCase,
    private val saveMedicationInfoToMedicationListUseCase: SaveMedicationInfoToMedicationListUseCase,
    private val editExistingMedicationInListUseCase: EditExistingMedicationInListUseCase
) : ViewModel() {
    private var medicationList = mutableListOf<MedicationInfo>()
    private val medicationName = mutableStateOf("viewmodel")
    private val medicationGrammage = mutableIntStateOf(9)
    private val medicationOptionalFlag = mutableStateOf("viewmodel")

    init {
        loadMedicationInfoFromDatabase()
    }

    private fun loadMedicationInfoFromDatabase() {
        viewModelScope.launch {
            try {
                getMedicationInfoFromDatabaseUseCase(getPatientIdUseCase())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveData() {
        viewModelScope.launch {
            try {
                saveMedicationInfoToDatabaseUseCase(
                    prepareMedicationDataToSaveItIntoDatabaseUseCase(
                        medicationList
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun addNewMedicationToList() {
        try {
            medicationList = saveMedicationInfoToMedicationListUseCase(
                MedicationInfo(
                    getPatientIdUseCase(),
                    medicationName.value,
                    medicationGrammage.intValue,
                    medicationOptionalFlag.value
                ), medicationList
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

/*
    private suspend fun editExistingMedication(medicationName: String, medicationGrammage: Int) {
        editExistingMedicationInListUseCase(medicationName, medicationGrammage)
    }
 */
}