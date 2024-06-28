package com.losrobotines.nuralign.feature_medication.domain.usecases.medication

import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val MEDICATION_A = MedicationInfo(1, 9, "A", 200, "Y")

class RemoveMedicationFromListUseCaseTest {
    private lateinit var userService: UserService
    private lateinit var medicationProvider: MedicationProvider
    private lateinit var useCase: RemoveMedicationFromListUseCase
    private lateinit var medicationList: List<MedicationInfo?>

    @BeforeEach
    fun setUp() {
        userService = mockk(relaxed = true)
        medicationProvider = mockk(relaxed = true)
        useCase = RemoveMedicationFromListUseCase(medicationProvider)
        medicationList = listOf(MEDICATION_A)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a medication is provided then remove it from the list`() = runBlocking {
        coEvery { medicationProvider.getMedicationList(any()) } returns medicationList
        val result = useCase.invoke(MEDICATION_A)

        coVerify { medicationProvider.deleteMedicationInfo(MEDICATION_A) }
        assertTrue(result.isSuccess)

        coEvery { medicationProvider.getMedicationList(any()) } returns emptyList()
        val updatedList = medicationProvider.getMedicationList(9)
        assertTrue(updatedList.isEmpty())
    }
}