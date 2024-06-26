package com.losrobotines.nuralign.feature_weekly_summary.domain.usecases

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CalculateWeeklyMoodAveragesUseCaseTest {

    private lateinit var getWeeklyMoodTrackerInfoUseCase: GetWeeklyMoodTrackerInfoUseCase
    private lateinit var calculateWeeklyMoodAveragesUseCase: CalculateWeeklyMoodAveragesUseCase

    @BeforeEach
    fun setUp() {
        getWeeklyMoodTrackerInfoUseCase = mockk(relaxed = true)
        calculateWeeklyMoodAveragesUseCase = CalculateWeeklyMoodAveragesUseCase(getWeeklyMoodTrackerInfoUseCase)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `execute returns correct average labels`() = runBlocking {
        // Given
        val patientId: Short = 123

        val mockMoodTrackerInfoList = listOf(
            MoodTrackerInfo(patientId, "2023-06-01", "3", "2", "note1", "note2", "1", "note3", "2", "note4"),
            MoodTrackerInfo(patientId, "2023-06-02", "4", "3", "note5", "note6", "2", "note7", "3", "note8"),
            MoodTrackerInfo(patientId, "2023-06-03", "2", "1", "note9", "note10", "1", "note11", "1", "note12"),
            MoodTrackerInfo(patientId, "2023-06-04", "3", "2", "note13", "note14", "3", "note15", "4", "note16"),
            MoodTrackerInfo(patientId, "2023-06-05", "4", "4", "note17", "note18", "2", "note19", "2", "note20")
        )

        coEvery { getWeeklyMoodTrackerInfoUseCase(patientId) } returns mockMoodTrackerInfoList

        // When
        val result = calculateWeeklyMoodAveragesUseCase(patientId)

        // Then
        assertEquals("Alto", result?.highestValueLabel)
        assertEquals("Moderado", result?.lowestValueLabel)
        assertEquals("Moderado", result?.irritableValueLabel)
        assertEquals("Moderado", result?.anxiousValueLabel)
    }

    @Test
    fun `execute returns null when no valid MoodTrackerInfo`() = runBlocking {
        // Given
        val patientId: Short = 123

        coEvery { getWeeklyMoodTrackerInfoUseCase(patientId) } returns emptyList()

        // When
        val result = calculateWeeklyMoodAveragesUseCase(patientId)

        // Then
        assertNull(result)
    }
}