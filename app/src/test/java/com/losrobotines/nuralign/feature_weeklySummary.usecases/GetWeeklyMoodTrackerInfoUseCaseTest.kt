package com.losrobotines.nuralign.feature_weekly_summary.domain.usecases

import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GetWeeklyMoodTrackerInfoUseCaseTest {

    private lateinit var weeklySummaryProvider: WeeklySummaryProvider
    private lateinit var getWeeklyMoodTrackerInfoUseCase: GetWeeklyMoodTrackerInfoUseCase

    @BeforeEach
    fun setUp() {
        weeklySummaryProvider = mockk(relaxed = true)
        getWeeklyMoodTrackerInfoUseCase = GetWeeklyMoodTrackerInfoUseCase(weeklySummaryProvider)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `execute returns list of MoodTrackerInfo`() = runBlocking {
        // Given
        val patientId: Short = 123
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        coEvery { weeklySummaryProvider.getMoodTracker(any(), any()) } returns mockk()

        // When
        val result = getWeeklyMoodTrackerInfoUseCase(patientId)

        // Then
        assertEquals(7, result.size)
        coVerify(exactly = 7) {
            weeklySummaryProvider.getMoodTracker(patientId, any())
        }
    }
}