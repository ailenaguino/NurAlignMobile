package com.losrobotines.nuralign.feature_routine.presentation.usescases
import com.losrobotines.nuralign.feature_routine.data.RoutineProviderImpl
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.models.Routine
import com.losrobotines.nuralign.feature_routine.domain.usescases.SaveRoutineUseCase
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
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

@OptIn(ExperimentalCoroutinesApi::class)
class SaveRoutineUseCaseTest {

    private lateinit var routineRepository: RoutineProviderImpl
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
        saveRoutineUseCase.invoke(ROUTINE.sleepTime, ROUTINE.activities)

        // Then
        coVerify(exactly = 1) { routineRepository.addRoutine(ROUTINE) }
    }
}