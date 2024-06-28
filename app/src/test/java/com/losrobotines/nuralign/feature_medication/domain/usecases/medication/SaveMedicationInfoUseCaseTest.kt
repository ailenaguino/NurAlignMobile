package com.losrobotines.nuralign.feature_medication.domain.usecases.medication

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

private val MEDICATION_A = MedicationInfo(1, 9, "A", 100, "Y")
private val MEDICATION_B = MedicationInfo(null, 9, "B", 100, "N")

class SaveMedicationInfoUseCaseTest {
    private lateinit var medicationProvider: MedicationProvider
    private lateinit var useCase: SaveMedicationInfoUseCase
    private lateinit var medicationList: List<MedicationInfo?>

    @BeforeEach
    fun setUp() {
        medicationProvider = mockk(relaxed = true)
        useCase = SaveMedicationInfoUseCase(medicationProvider)
        medicationList = listOf(MEDICATION_A)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Disabled
    @Test
    fun `when a new medication is provided, save it`() =
        runBlocking {
            coEvery { medicationProvider.getMedicationList(any()) } returns medicationList
            val result = useCase.invoke(MEDICATION_B)

            coVerify { medicationProvider.saveMedicationInfo(MEDICATION_B) }
            assertTrue(result.isSuccess)

            coEvery { medicationProvider.getMedicationList(any()) } returns listOf(
                MEDICATION_A,
                MEDICATION_B
            )
            val updatedList = medicationProvider.getMedicationList(9)
            assertTrue(updatedList.contains(MEDICATION_A))
        }
}
