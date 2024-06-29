package com.losrobotines.nuralign.feature_weeklySummary.usecases

import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import com.losrobotines.nuralign.feature_weekly_summary.domain.usecases.GetWeeklyMoodTrackerInfoUseCase
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

private const val PATIENT_ID: Short = 123
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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        coEvery { weeklySummaryProvider.getMoodTracker(any(), any()) } returns mockk()

        // When
        val result = getWeeklyMoodTrackerInfoUseCase(PATIENT_ID)

        // Then
        assertEquals(7, result.size)
        coVerify(exactly = 7) {
            weeklySummaryProvider.getMoodTracker(PATIENT_ID, any())
        }
    }
}