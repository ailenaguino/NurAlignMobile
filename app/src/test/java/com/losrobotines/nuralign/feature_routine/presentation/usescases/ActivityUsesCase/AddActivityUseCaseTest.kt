package com.losrobotines.nuralign.feature_routine.presentation.usescases.ActivityUsesCase

import org.junit.jupiter.api.Assertions.*
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.models.Routine
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.AddActivityUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.RemoveActivityUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.RemoveSelectedDayFromActivityUseCase
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
private val NEW_ACTIVITY = Activity(
    name = "Yoga",
    time = "18:00",
    days = listOf("Lu", "Mi", "Vi")
)

@OptIn(ExperimentalCoroutinesApi::class)
class AddActivityUseCaseTest {

    private lateinit var routineProvider: RoutineProvider
    private lateinit var addActivityUseCase: AddActivityUseCase

    @BeforeEach
    fun setUp() {
        routineProvider = mockk(relaxed = true)
        addActivityUseCase = AddActivityUseCase(routineProvider)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke adds activity and updates routine`() = runBlocking {
        // Given

        val updatedActivities =   ROUTINE.activities.toMutableList().apply { add(NEW_ACTIVITY) }
        val updatedRoutine =   ROUTINE.copy(activities = updatedActivities)

        coEvery { routineProvider.getRoutine() } returns  ROUTINE
        coEvery { routineProvider.addRoutine(any()) } answers { /* No-op */ }

        // When
        addActivityUseCase.invoke(NEW_ACTIVITY)

        // Then
        coVerify(exactly = 1) { routineProvider.getRoutine() }

        // Capturar y verificar la rutina actualizada
        val capturedRoutine = slot<Routine>()
        coVerify(exactly = 1) { routineProvider.addRoutine(capture(capturedRoutine)) }

        val capturedActivities = capturedRoutine.captured.activities
        val capturedActivity = capturedActivities.find { it.name == NEW_ACTIVITY.name }
        assertEquals(NEW_ACTIVITY.time, capturedActivity!!.time)
        assertEquals(NEW_ACTIVITY.days, capturedActivity.days)
        assertEquals(capturedActivities.size, updatedRoutine.activities.size)
    }
}