package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import io.mockk.Awaits
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SaveSleepTrackerInfoUseCaseTest {
    private lateinit var authRepository: AuthRepository
    private lateinit var sleepTrackerProvider: SleepTrackerProvider
    private lateinit var formatTimeUseCase: FormatTimeUseCase
    private lateinit var saveSleepTrackerInfoUseCase: SaveSleepTrackerInfoUseCase

    @BeforeEach
    fun setUp() {
        sleepTrackerProvider = mockk(relaxed = true)
        formatTimeUseCase = mockk(relaxed = true)
        authRepository = mockk(relaxed = true)
        saveSleepTrackerInfoUseCase =
            SaveSleepTrackerInfoUseCase(authRepository,formatTimeUseCase, sleepTrackerProvider)
    }

    @Disabled
    @Test
    fun `execute calls saveSleepData on sleepTrackerProvider`() = runBlocking {
        // Given
        val patientId: Short = 1
        val currentDate = "2024-06-07"
        val sleepHours = 8
        val bedTime = "22:00"
        val negativeThoughts = true
        val anxiousBeforeSleep = false
        val sleptThroughNight = true
        val additionalNotes = "Good sleep"

        val formattedBedTime = "2200"
        every { formatTimeUseCase.removeColonFromTime(bedTime) } returns formattedBedTime

        val expectedSleepInfo = SleepInfo(
            patientId,
            currentDate,
            sleepHours.toShort(),
            formattedBedTime,
            "T",
            "F",
            "T",
            additionalNotes
        )

        coEvery { sleepTrackerProvider.saveSleepData(expectedSleepInfo) } just Awaits

        // When
        saveSleepTrackerInfoUseCase(
            patientId,
            currentDate,
            sleepHours,
            bedTime,
            negativeThoughts,
            anxiousBeforeSleep,
            sleptThroughNight,
            additionalNotes
        )

        // Then
        coVerify(exactly = 1) { sleepTrackerProvider.saveSleepData(expectedSleepInfo) }
    }
}