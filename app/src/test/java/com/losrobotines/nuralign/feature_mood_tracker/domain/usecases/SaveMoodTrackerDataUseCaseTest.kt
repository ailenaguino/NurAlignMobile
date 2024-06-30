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

class SaveMoodTrackerDataUseCaseTest {
    private lateinit var moodTrackerProvider: MoodTrackerProvider
    private lateinit var useCase: SaveMoodTrackerDataUseCase
    private lateinit var moodTrackerInfo: MoodTrackerInfo

    @BeforeEach
    fun setUp() {
        moodTrackerProvider = mockk(relaxed = true)
        useCase = SaveMoodTrackerDataUseCase(moodTrackerProvider)
        moodTrackerInfo = MOOD_TRACKER_INFO
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a mood tracker is provided then save it`() = runBlocking {
        coEvery { moodTrackerProvider.getMoodTrackerInfo(any(), any()) } returns null

        val result = useCase.invoke(moodTrackerInfo)
        coVerify { moodTrackerProvider.saveMoodTrackerInfo(any()) }
        assertTrue(result.isSuccess)

        coEvery { moodTrackerProvider.getMoodTrackerInfo(1, "2024-06-29") } returns moodTrackerInfo
        val updatedMoodTrackerInfo = moodTrackerProvider.getMoodTrackerInfo(1, "2024-06-29")
        assertEquals(moodTrackerInfo, updatedMoodTrackerInfo)
    }
}