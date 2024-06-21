package com.losrobotines.nuralign.feature_routine.presentation.usescases.ActivityUsesCase

import org.junit.jupiter.api.Assertions.*
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.models.Routine
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.UpdateActivityNameUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.UpdateActivityTimeUseCase
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
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
class UpdateActivityNameUseCaseTest {

    private lateinit var routineProvider: RoutineProvider
    private lateinit var updateActivityNameUseCase: UpdateActivityNameUseCase

    @BeforeEach
    fun setUp() {
        routineProvider = mockk(relaxed = true)
        updateActivityNameUseCase = UpdateActivityNameUseCase(routineProvider)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke updates activity name and saves routine`() = runBlocking {
        // Given
        val activityToUpdate = ROUTINE.activities[0]
        val newName = "Running"

        coEvery { routineProvider.getRoutine() } returns ROUTINE
        coEvery { routineProvider.addRoutine(any()) } just Runs

        // Captor para capturar el argumento pasado a addRoutine
        val routineSlot = slot<Routine>()

        // When
        updateActivityNameUseCase.invoke(activityToUpdate, newName)

        // Then
        coVerify(exactly = 1) { routineProvider.getRoutine() }
        coVerify(exactly = 1) { routineProvider.addRoutine(capture(routineSlot)) }

        val capturedRoutine = routineSlot.captured
        val capturedActivity = capturedRoutine.activities.find { it.time == activityToUpdate.time }

        assertNotNull(capturedActivity)
        assertEquals(newName, capturedActivity!!.name)
    }
}