package com.losrobotines.nuralign.feature_login.domain.usecases

import io.mockk.MockKAnnotations
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FormatBirthDateUseCaseTest{
    lateinit var formatBirthDateUseCase: FormatBirthDateUseCase

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        formatBirthDateUseCase = FormatBirthDateUseCase()
    }

    @Test
    fun `if a date is formatted as ddMMyyyy then is transformed to yyyy-MM-dd format`(){
        // Arrange
        val inputDate = "15052000"
        val expectedFormattedDate = "2000-05-15"

        // Act
        val result = formatBirthDateUseCase(inputDate)

        // Assert
        assertEquals(expectedFormattedDate, result)

    }

    @Test
    fun `given invalid date, when invoking use case, then return null`() {
        // Arrange
        val inputDate = "12345" // Invalid date format

        // Act
        val result = formatBirthDateUseCase(inputDate)

        // Assert
        assertEquals(null, result)
    }

}