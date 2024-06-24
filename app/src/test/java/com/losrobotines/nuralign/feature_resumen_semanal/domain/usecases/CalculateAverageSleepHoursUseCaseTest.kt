package com.losrobotines.nuralign.feature_resumen_semanal.domain.usecases

import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CalculateAverageSleepHoursUseCaseTest {

    private lateinit var calculateAverageSleepHoursUseCase: CalculateAverageSleepHoursUseCase

    @BeforeEach
    fun setUp() {
        calculateAverageSleepHoursUseCase = CalculateAverageSleepHoursUseCase()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke returns correct average sleep hours`() = runBlocking {
        // Given
        val sleepInfo1 = mockk<SleepInfo> {
            every { sleepHours } returns 8
        }
        val sleepInfo2 = mockk<SleepInfo> {
            every { sleepHours } returns 6
        }
        val sleepInfo3 = mockk<SleepInfo> {
            every { sleepHours } returns 7
        }
        val sleepInfoList = listOf(sleepInfo1, sleepInfo2, sleepInfo3)

        // When
        val result = calculateAverageSleepHoursUseCase.invoke(sleepInfoList)

        // Then
        assertEquals(7.0, result, 0.0)
    }

    @Test
    fun `invoke returns correct average sleep hours with null values`() = runBlocking {
        // Given
        val sleepInfo1 = mockk<SleepInfo> {
            every { sleepHours } returns 8
        }
        val sleepInfo2 = null
        val sleepInfo3 = mockk<SleepInfo> {
            every { sleepHours } returns 7
        }
        val sleepInfoList = listOf(sleepInfo1, sleepInfo2, sleepInfo3)

        // When
        val result = calculateAverageSleepHoursUseCase.invoke(sleepInfoList)

        // Then
        assertEquals(7.5, result, 0.0)
    }

    @Test
    fun `invoke returns 0_0 when list is empty`() = runBlocking {
        // Given
        val sleepInfoList = emptyList<SleepInfo?>()

        // When
        val result = calculateAverageSleepHoursUseCase.invoke(sleepInfoList)

        // Then
        assertEquals(0.0, result, 0.0)
    }
}