package com.losrobotines.nuralign.feature_mood_tracker.domain.usecases

import com.losrobotines.nuralign.feature_mood_tracker.domain.MoodTrackerProvider
import com.losrobotines.nuralign.feature_mood_tracker.domain.models.MoodTrackerInfo
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val MOOD_TRACKER_INFO =
    MoodTrackerInfo(
        1,
        "2024-06-29",
        "1",
        "1",
        "High note",
        "Lowe note",
        "1",
        "Irri note",
        "1",
        "Anxi note"
    )

class UpdateMoodTrackerUseCaseTest {
    private lateinit var moodTrackerProvider: MoodTrackerProvider
    private lateinit var useCase: UpdateMoodTrackerUseCase
    private lateinit var moodTrackerInfo: MoodTrackerInfo

    @BeforeEach
    fun setUp() {
        moodTrackerProvider = mockk(relaxed = true)
        useCase = UpdateMoodTrackerUseCase(moodTrackerProvider)
        moodTrackerInfo = MOOD_TRACKER_INFO
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a mood tracker is provided then update it`() = runBlocking {
        coEvery { moodTrackerProvider.getMoodTrackerInfo(any(), any()) } returns moodTrackerInfo

        val moodUpdated = moodTrackerInfo.copy(highestValue = "2", lowestValue = "2", irritableValue = "2", anxiousValue = "2")

        val result = useCase.invoke(moodUpdated)
        coVerify { moodTrackerProvider.updateMoodTrackerInfo(any()) }
        assertTrue(result.isSuccess)

        coEvery { moodTrackerProvider.getMoodTrackerInfo(1, "2024-06-29") } returns moodUpdated
        val updatedMoodTrackerInfo = moodTrackerProvider.getMoodTrackerInfo(1, "2024-06-29")
        assertEquals(moodUpdated, updatedMoodTrackerInfo)
    }
}