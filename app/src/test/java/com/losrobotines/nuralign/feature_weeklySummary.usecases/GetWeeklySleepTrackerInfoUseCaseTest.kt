package com.losrobotines.nuralign.feature_weekly_summary.domain.usecases

import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import org.junit.jupiter.api.Assertions.*

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.*


class GetWeeklySleepTrackerInfoUseCaseTest {

    private lateinit var weeklySummaryProvider: WeeklySummaryProvider
    private lateinit var getWeeklySleepTrackerInfoUseCase: GetWeeklySleepTrackerInfoUseCase

    @BeforeEach
    fun setUp() {
        weeklySummaryProvider = mockk(relaxed = true)
        getWeeklySleepTrackerInfoUseCase = GetWeeklySleepTrackerInfoUseCase(weeklySummaryProvider)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `execute returns list of SleepInfo`() = runBlocking {
        // Given
        val patientId: Short = 123
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        coEvery { weeklySummaryProvider.getSleepTracker(any(), any()) } returns mockk()

        // When
        val result = getWeeklySleepTrackerInfoUseCase(patientId)

        // Then
        assertEquals(7, result.size)
        coVerify(exactly = 7) { weeklySummaryProvider.getSleepTracker(patientId, any()) }
    }
}