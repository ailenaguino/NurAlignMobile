package com.losrobotines.nuralign.feature_medication.domain.usecases.medication

import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val MEDICATION_A = MedicationInfo(1, 9, "A", 200, "Y")
private val MEDICATION_B = MedicationInfo(null, 9, "B", 100, "N")

class SaveMedicationInfoUseCaseTest {

    private lateinit var userService: UserService
    private lateinit var medicationProvider: MedicationProvider
    private lateinit var listProvided: MutableList<MedicationInfo?>
    private lateinit var saveMedListUseCase: SaveMedicationInfoUseCase

    @BeforeEach
    fun setUp() {
        userService = mockk(relaxed = true)
        medicationProvider = mockk(relaxed = true)
        listProvided = mutableListOf(MEDICATION_A)
        saveMedListUseCase = SaveMedicationInfoUseCase(userService, medicationProvider)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a new medication is provided, save it`() =
        runBlocking {
            coEvery { userService.getPatientId() } returns Result.success(9)
            coEvery { userService.getMedicationList(any()) } returns Result.success(listProvided)
            coEvery { medicationProvider.saveMedicationInfo(any()) } returns true
            val result = saveMedListUseCase.invoke(MEDICATION_B)

            assertTrue(result.isSuccess)
        }
}
