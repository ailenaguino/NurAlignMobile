package com.losrobotines.nuralign.feature_routine.presentation.usescases

import com.losrobotines.nuralign.feature_routine.data.RoutineProviderImpl
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.models.Routine
import com.losrobotines.nuralign.feature_routine.domain.usescases.LoadRoutineUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


private val ROUTINE = Routine(
    id = 0,
    sleepTime = "22:30",
    activities = listOf(
        Activity(
            name = "Gimnasio",
            time = "20:00",
            days = listOf("Lu", "Mi", "Vi")
        ),
        Activity(
            name = "Clases de ingles",
            time = "17:30",
            days = listOf("Ma", "Jue")
        )
    )
)

class LoadRoutineUseCaseTest {

    private lateinit var routineRepository: RoutineProvider
    private lateinit var loadRoutineUseCase: LoadRoutineUseCase

    @BeforeEach
    fun setUp() {
        routineRepository = mockk()
        loadRoutineUseCase = LoadRoutineUseCase(routineRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `execute calls getRoutine on routineRepository and returns routine`() = runBlocking {
        // Given
        coEvery { routineRepository.getRoutine() } returns ROUTINE

        // When
        val result = loadRoutineUseCase()

        // Then
        coVerify(exactly = 1) { routineRepository.getRoutine() }
        assertEquals(ROUTINE, result)
    }
}