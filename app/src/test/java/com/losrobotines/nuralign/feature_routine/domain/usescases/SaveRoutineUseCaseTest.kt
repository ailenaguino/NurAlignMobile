package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.data.RoutineRepositoryDatabase
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SaveRoutineUseCaseTest {
    private lateinit var routineRepository: RoutineRepositoryDatabase
    private lateinit var saveRoutineUseCase: SaveRoutineUseCase

    @BeforeEach
    fun setUp() {
        routineRepository = mockk()
        saveRoutineUseCase = SaveRoutineUseCase(routineRepository)
    }

    @Test
    fun `execute calls addRoutine on routineRepository`() = runBlocking {
        // Given
        val routine = RoutineEntity(
            id = 1,
            sleepTime = "22:30",
            activity = "Gimnasio",
            activityTime = "20:00",
            activityDays = listOf("Lu", "Mi", "Vi")
        )

        coEvery { routineRepository.addRoutine(routine) } returns Unit

        // When
        saveRoutineUseCase.execute(routine)

        // Then
        coVerify(exactly = 1) { routineRepository.addRoutine(routine) }
    }
}