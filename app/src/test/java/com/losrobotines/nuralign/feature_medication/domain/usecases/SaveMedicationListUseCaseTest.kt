package com.losrobotines.nuralign.feature_medication.domain.usecases

import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val MEDICATION_A = MedicationInfo(9, "A", 200, "Y")
private val MEDICATION_B = MedicationInfo(9, "B", 100, "N")
private val MEDICATION_C = MedicationInfo(9, "C", 300, "Y")

class SaveMedicationListUseCaseTest {

    private lateinit var userService: UserService
    private lateinit var newMedList: MutableList<MedicationInfo?>
    private lateinit var conflictMedList: MutableList<MedicationInfo?>
    private lateinit var listProvided: MutableList<MedicationInfo?>
    private lateinit var resultList: MutableList<MedicationInfo?>
    private lateinit var saveMedListUseCase: SaveMedicationListUseCase

    @BeforeEach
    fun setUp() {
        userService = mockk(relaxed = true)
        newMedList = mutableListOf(MEDICATION_B, MEDICATION_C)
        conflictMedList = mutableListOf(MEDICATION_A, MEDICATION_B, MEDICATION_C)
        listProvided = mutableListOf(MEDICATION_A)
        resultList = mutableListOf(MEDICATION_A, MEDICATION_B, MEDICATION_C)
        saveMedListUseCase = SaveMedicationListUseCase(userService)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a new list is provided, save it`() =
        runBlocking {
            coEvery { userService.getMedicationList(userService.getPatientId()) } returns listProvided

            coEvery { userService.saveMedicationList(any()) } returns true
            val result = saveMedListUseCase(newMedList)

            assertEquals(true, result)
        }
}