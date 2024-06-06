package com.losrobotines.nuralign.feature_medication.domain.usecases

import com.losrobotines.nuralign.feature_home.domain.usecases.GetPatientIdUseCase
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

class PrepareMedicationDataToSaveItIntoDatabaseUseCaseTest {

    private lateinit var getMedListFromDB: GetMedicationInfoFromDatabaseUseCase
    private lateinit var getPatientId: GetPatientIdUseCase
    private lateinit var newMedList: MutableList<MedicationInfo?>
    private lateinit var conflictMedList: MutableList<MedicationInfo?>
    private lateinit var listFromDB: MutableList<MedicationInfo?>
    private lateinit var resultList: MutableList<MedicationInfo?>
    private lateinit var useCase: PrepareMedicationDataToSaveItIntoDatabaseUseCase

    @BeforeEach
    fun setUp() {
        getMedListFromDB = mockk(relaxed = true)
        getPatientId = mockk(relaxed = true)
        newMedList = mutableListOf(MEDICATION_B, MEDICATION_C)
        conflictMedList = mutableListOf(MEDICATION_A, MEDICATION_B, MEDICATION_C)
        listFromDB = mutableListOf(MEDICATION_A)
        resultList = mutableListOf(MEDICATION_A, MEDICATION_B, MEDICATION_C)
        useCase = PrepareMedicationDataToSaveItIntoDatabaseUseCase(getMedListFromDB, getPatientId)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a new list is provided, then merge it with database list`() =
        runBlocking {
            coEvery { getMedListFromDB.invoke(getPatientId.invoke()) } returns listFromDB
            val result = useCase(newMedList)

            assertEquals(resultList, result)
        }


    @Test
    fun `when a med in new list already exists in database, then don't count it when merge`() =
        runBlocking {
            coEvery { getMedListFromDB.invoke(getPatientId.invoke()) } returns listFromDB
            val result = useCase(conflictMedList)

            assertEquals(resultList, result)
        }
}