package com.losrobotines.nuralign.feature_routine.domain.usescases

import com.losrobotines.nuralign.feature_routine.data.RoutineRepositoryDatabase
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import com.losrobotines.nuralign.feature_routine.domain.Routine
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


private val ROUTINE = Routine(
    id = 0,
    sleepTime = "22:30",
    activity = "Gimnasio",
    activityTime = "20:00",
    activityDays = listOf("Lu", "Mi", "Vi")
)

class SaveRoutineUseCaseTest {

    private lateinit var routineRepository: RoutineRepositoryDatabase
    private lateinit var saveRoutineUseCase: SaveRoutineUseCase

    @BeforeEach
    fun setUp() {
        routineRepository = mockk(relaxed = true)
        saveRoutineUseCase = SaveRoutineUseCase(routineRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `execute calls addRoutine on routineRepository`() = runBlocking {
        // When
        saveRoutineUseCase(ROUTINE)

        // Then
        coVerify(exactly = 1) { routineRepository.addRoutine(ROUTINE) }

    }
}