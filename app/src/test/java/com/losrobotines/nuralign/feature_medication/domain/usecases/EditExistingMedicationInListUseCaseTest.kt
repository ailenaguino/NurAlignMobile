package com.losrobotines.nuralign.feature_medication.domain.usecases

import android.util.Log
import com.losrobotines.nuralign.feature_login.domain.services.UserService
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

    private lateinit var userService: UserService
    private lateinit var editMedUseCase: EditExistingMedicationInListUseCase
    private lateinit var listFromDB: MutableList<MedicationInfo?>

    @BeforeEach
    fun setUp() {
        userService = mockk(relaxed = true)
        editMedUseCase = EditExistingMedicationInListUseCase(userService)
        listFromDB = mutableListOf(MEDICATION_A)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when old medication exists in medication list then change the old med with the new variables`() =
        runBlocking {
            givenOldMedicationHas(MEDICATION_A)
            val newMedication = editMedUseCase.invoke(NAME_B, DOSE_100, OPTIONAL_N, MEDICATION_A)

            newMedication!!.has(NAME_B, DOSE_100, OPTIONAL_N)
        }

    @Test
    fun `when old medication doesn't exists in medication list, then return null`() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        runBlocking {
            coEvery { userService.getMedicationList(userService.getPatientId()) } returns listFromDB
            val result = editMedUseCase.invoke("C", 300, "Y", MEDICATION_B)

            assertEquals(null, result)
        }
    }

    private fun givenOldMedicationHas(medication: MedicationInfo) {
        coEvery {
            userService.getMedicationList(userService.getPatientId())
        } returns mutableListOf(medication)
    }

    private fun MedicationInfo.has(
        medicationName: String,
        medicationGrammage: Int,
        medicationOptionalFlag: String
    ) {
        assertEquals(this.medicationName, medicationName)
        assertEquals(this.medicationGrammage, medicationGrammage)
        assertEquals(this.medicationOptionalFlag, medicationOptionalFlag)
    }
}