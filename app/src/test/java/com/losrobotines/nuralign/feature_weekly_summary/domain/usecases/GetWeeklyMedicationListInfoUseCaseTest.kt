package com.losrobotines.nuralign.feature_weekly_summary.domain.usecases

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import org.junit.jupiter.api.Assertions.*

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.text.SimpleDateFormat
import java.util.*

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
        val patientId: Short = 123
        val mockMedicationList = listOf<MedicationInfo?>(mockk(relaxed = true))

        coEvery { weeklySummaryProvider.getMedicationListInfo(patientId) } returns mockMedicationList

        // When
        val result = getWeeklyMedicationListInfoUseCase(patientId)

        // Then
        assertTrue(result.isNotEmpty())
        coVerify(exactly = 1) { weeklySummaryProvider.getMedicationListInfo(patientId) }
    }
}