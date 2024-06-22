package com.losrobotines.nuralign.feature_routine.presentation.usescases.activityusescase

import org.junit.jupiter.api.Assertions.*
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.models.Routine
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
@OptIn(ExperimentalCoroutinesApi::class)
class RemoveSelectedDayFromActivityUseCaseTest {

    private lateinit var routineProvider: RoutineProvider
    private lateinit var removeSelectedDayFromActivityUseCase: RemoveSelectedDayFromActivityUseCase

    @BeforeEach
    fun setUp() {
        routineProvider = mockk(relaxed = true)
        removeSelectedDayFromActivityUseCase = RemoveSelectedDayFromActivityUseCase(routineProvider)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke removes selected day from activity and saves routine`() = runBlocking {
        // Given
        val activityToUpdate = ROUTINE.activities[0]
        val dayToRemove = "Mi"
        val updatedDays = activityToUpdate.days.toMutableList().apply { remove(dayToRemove) }
        val updatedActivity = activityToUpdate.copy(days = updatedDays)
        val updatedActivities = ROUTINE.activities.toMutableList().apply {
            this[0] = updatedActivity
        }
        val updatedRoutine = ROUTINE.copy(activities = updatedActivities)

        coEvery { routineProvider.getRoutine() } returns ROUTINE
        coEvery { routineProvider.addRoutine(any()) } just Runs

        val routineSlot = slot<Routine>()

        // When
        removeSelectedDayFromActivityUseCase.invoke(activityToUpdate, dayToRemove)

        // Then
        coVerify(exactly = 1) { routineProvider.getRoutine() }
        coVerify(exactly = 1) { routineProvider.addRoutine(capture(routineSlot)) }

        val capturedRoutine = routineSlot.captured
        val capturedActivity = capturedRoutine.activities.find { it.name == activityToUpdate.name }

        assertNotNull(capturedActivity)
        assertFalse(capturedActivity!!.days.contains(dayToRemove))
    }
}