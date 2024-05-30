package com.losrobotines.nuralign.feature_sleep.domain.usecases

import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SaveSleepTrackerInfoUseCaseTest {

    private lateinit var sleepRepository: SleepRepository
    private lateinit var saveSleepTrackerInfoUseCase: SaveSleepTrackerInfoUseCase

    @BeforeEach
    fun setUp() {
        sleepRepository = mockk()
        saveSleepTrackerInfoUseCase = SaveSleepTrackerInfoUseCase(sleepRepository)
    }

    @Test
    fun `execute calls saveSleepData on sleepRepository`() = runBlocking {
        // Given
        val sleepInfo = SleepInfo(
            patientId = 1,
            effectiveDate = "2024-05-28",
            sleepHours = 8,
            bedTime = "2200",
            negativeThoughtsFlag = "N",
            anxiousFlag = "N",
            sleepStraightFlag = "Y",
            sleepNotes = "Good sleep"
        )

        coEvery { sleepRepository.saveSleepData(sleepInfo) } returns Unit

        // When
        saveSleepTrackerInfoUseCase.execute(sleepInfo)

        // Then
        coVerify(exactly = 1) { sleepRepository.saveSleepData(sleepInfo) }
    }
}