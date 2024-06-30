package com.losrobotines.nuralign.feature_weeklySummary.usecases

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.GetWeeklyMedicationListInfoUseCase
import org.junit.jupiter.api.Assertions.*

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
private const val PATIENT_ID: Short = 123
class GetWeeklyMedicationListInfoUseCaseTest {

    private lateinit var weeklySummaryProvider: WeeklySummaryProvider
    private lateinit var getWeeklyMedicationListInfoUseCase: GetWeeklyMedicationListInfoUseCase

    @BeforeEach
    fun setUp() {
        weeklySummaryProvider = mockk(relaxed = true)
        getWeeklyMedicationListInfoUseCase = GetWeeklyMedicationListInfoUseCase(weeklySummaryProvider)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `execute returns list of MedicationInfo`() = runBlocking {
        // Given

        val mockMedicationList = listOf<MedicationInfo?>(mockk(relaxed = true))

        coEvery { weeklySummaryProvider.getMedicationListInfo(PATIENT_ID) } returns mockMedicationList

        // When
        val result = getWeeklyMedicationListInfoUseCase(PATIENT_ID)

        // Then
        assertTrue(result.isNotEmpty())
        coVerify(exactly = 1) { weeklySummaryProvider.getMedicationListInfo(PATIENT_ID) }
    }
}