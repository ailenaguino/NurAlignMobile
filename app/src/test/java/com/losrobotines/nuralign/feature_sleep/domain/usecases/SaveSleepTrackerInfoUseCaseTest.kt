package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val SLEEP_INFO =
    SleepInfo(1, "2024-06-07", 8, "22:00", "T", "F", "T", "Good sleep")

class SaveSleepTrackerInfoUseCaseTest {
    private lateinit var sleepTrackerProvider: SleepTrackerProvider
    private lateinit var formatTimeUseCase: FormatTimeUseCase
    private lateinit var useCase: SaveSleepTrackerInfoUseCase
    private lateinit var sleepInfo: SleepInfo

    @BeforeEach
    fun setUp() {
        sleepTrackerProvider = mockk(relaxed = true)
        formatTimeUseCase = mockk(relaxed = true)
        useCase =
            SaveSleepTrackerInfoUseCase(formatTimeUseCase, sleepTrackerProvider)
        sleepInfo = SLEEP_INFO
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a sleep tracker is provided then save it`() = runBlocking {
        coEvery { sleepTrackerProvider.getSleepData(any(), any()) } returns null
        coEvery { formatTimeUseCase.removeColonFromTime(sleepInfo.bedTime) } returns "2200"

        val result = useCase.invoke(
            sleepInfo.patientId,
            sleepInfo.effectiveDate,
            sleepInfo.sleepHours,
            sleepInfo.bedTime,
            negativeThoughts = true,
            anxiousBeforeSleep = false,
            sleptThroughNight = true,
            sleepInfo.sleepNotes
        )
        coVerify { sleepTrackerProvider.saveSleepData(any()) }
        assertTrue(result.isSuccess)

        coEvery { sleepTrackerProvider.getSleepData(1, "2024-06-07") } returns sleepInfo
        val updatedSleepInfo = sleepTrackerProvider.getSleepData(1, "2024-06-07")
        assertEquals(sleepInfo, updatedSleepInfo)
    }
}