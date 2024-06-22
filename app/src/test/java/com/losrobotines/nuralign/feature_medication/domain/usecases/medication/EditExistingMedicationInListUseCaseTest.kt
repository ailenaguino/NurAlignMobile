package com.losrobotines.nuralign.feature_medication.domain.usecases.medication

import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val MEDICATION_A = MedicationInfo(1, 9, "A", 200, "Y")
private const val NAME_B: String = "B"
private const val DOSE_100: Int = 100
private const val OPTIONAL_N: String = "N"
private val MEDICATION_B = MedicationInfo(null, 9, NAME_B, DOSE_100, OPTIONAL_N)

class EditExistingMedicationInListUseCaseTest {

    private lateinit var userService: UserService
    private lateinit var medicationProvider: MedicationProvider
    private lateinit var editMedUseCase: EditExistingMedicationInListUseCase
    private lateinit var listFromDB: MutableList<MedicationInfo?>

    @BeforeEach
    fun setUp() {
        userService = mockk(relaxed = true)
        medicationProvider = mockk(relaxed = true)
        editMedUseCase = EditExistingMedicationInListUseCase(medicationProvider)
        listFromDB = mutableListOf(MEDICATION_A)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when old medication exists in medication list then change the old med with the new variables`() =
        runBlocking {
            coEvery { userService.getMedicationList(userService.getPatientId().getOrNull()!!) } returns Result.success(listFromDB)
            val newList = editMedUseCase.invoke(
                NAME_B,
                DOSE_100,
                OPTIONAL_N,
                MEDICATION_A,
                listFromDB
            )

            assertTrue(newList.isSuccess)
        }

    @Test
    fun `when old medication doesn't exists in medication list, then return null`() {
        runBlocking {
            coEvery { userService.getMedicationList(userService.getPatientId().getOrNull()!!) } returns Result.success(listFromDB)
            val result = editMedUseCase.invoke("C", 300, "Y", MEDICATION_B, listFromDB)

            assertTrue(result.isFailure)
        }
    }
}