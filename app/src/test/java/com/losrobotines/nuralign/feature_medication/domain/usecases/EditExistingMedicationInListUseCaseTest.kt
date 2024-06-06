package com.losrobotines.nuralign.feature_medication.domain.usecases

import android.util.Log
import com.losrobotines.nuralign.feature_home.domain.usecases.GetPatientIdUseCase
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val MEDICATION_A = MedicationInfo(9, "A", 200, "Y")
private const val NAME_B: String = "B"
private const val DOSE_100: Int = 100
private const val OPTIONAL_N: String = "N"
private val MEDICATION_B = MedicationInfo(9, NAME_B, DOSE_100, OPTIONAL_N)

class EditExistingMedicationInListUseCaseTest {

    private lateinit var getMedFromDB: GetMedicationInfoFromDatabaseUseCase
    private lateinit var getPatientId: GetPatientIdUseCase
    private lateinit var useCase: EditExistingMedicationInListUseCase
    private lateinit var listFromDB: MutableList<MedicationInfo?>

    @BeforeEach
    fun setUp() {
        getMedFromDB = mockk(relaxed = true)
        getPatientId = mockk(relaxed = true)
        useCase = EditExistingMedicationInListUseCase(getMedFromDB, getPatientId)
        listFromDB = mutableListOf(MEDICATION_A)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when old medication exists in medication list from db then change the old med with the new variables`() =
        runBlocking {
            coEvery { getMedFromDB.invoke(getPatientId.invoke()) } returns listFromDB
            val result = useCase.invoke(NAME_B, DOSE_100, OPTIONAL_N, MEDICATION_A)

            assertEquals(MEDICATION_B, result)
        }

    @Test
    fun `when old medication doesn't exists in medication list from db, then return null`() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        runBlocking {
            coEvery { getMedFromDB.invoke(getPatientId.invoke()) } returns listFromDB
            val result = useCase.invoke("C", 300, "Y", MEDICATION_B)

            assertEquals(null, result)
        }
    }
}