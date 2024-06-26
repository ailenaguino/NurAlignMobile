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

private val SLEEP_INFO_1 = mockk<SleepInfo> {
    every { sleepHours } returns 8
}
private val SLEEP_INFO_2 = mockk<SleepInfo> {
    every { sleepHours } returns 6
}
private val SLEEP_INFO_3 = mockk<SleepInfo> {
    every { sleepHours } returns 7
}
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
        val sleepInfoList = listOf(SLEEP_INFO_1, SLEEP_INFO_2, SLEEP_INFO_3)

        // When
        val result = calculateAverageSleepHoursUseCase.invoke(sleepInfoList)

        // Then
        assertEquals(7.0, result, 0.0)
    }

    @Test
    fun `invoke returns correct average sleep hours with null values`() = runBlocking {
        // Given
        val sleepInfoList = listOf(SLEEP_INFO_1, null, SLEEP_INFO_3)

        // When
        val result = calculateAverageSleepHoursUseCase(sleepInfoList)

        // Then
        assertEquals(7.5, result, 0.0)
    }

    @Test
    fun `invoke returns 0_0 when list is empty`() = runBlocking {
        // Given
        val sleepInfoList = emptyList<SleepInfo?>()

        // When
        val result = calculateAverageSleepHoursUseCase(sleepInfoList)

        // Then
        assertEquals(0.0, result, 0.0)
    }
}