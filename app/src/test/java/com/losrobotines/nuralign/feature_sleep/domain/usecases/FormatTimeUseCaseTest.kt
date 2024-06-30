package com.losrobotines.nuralign.feature_sleep.domain.usecases

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val timeFormatted = "2034"

private const val timeWithoutFormatting = "20:34"

class FormatTimeUseCaseTest {

    private lateinit var formatTimeUseCase: FormatTimeUseCase

    @BeforeEach
    fun setUp() {
        formatTimeUseCase = FormatTimeUseCase()
    }

    @Test
    fun `addColonToTime formats time correctly`() {
        val formattedTime = formatTimeUseCase.addColonToTime(timeFormatted)
        assertEquals(timeWithoutFormatting, formattedTime)
    }

    @Test
    fun `removeColonFromTime formats time correctly`() {
        val formattedTime = formatTimeUseCase.removeColonFromTime(timeWithoutFormatting)
        assertEquals(timeFormatted, formattedTime)
    }


}