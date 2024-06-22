package com.losrobotines.nuralign.feature_routine.presentation.usescases.activityusescase

import org.junit.jupiter.api.Assertions.*
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.models.Routine
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityUsesCases.AddSelectedDayToActivityUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
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
class AddSelectedDayToActivityUseCaseTest {

    private lateinit var routineProvider: RoutineProvider
    private lateinit var addSelectedDayToActivityUseCase: AddSelectedDayToActivityUseCase

    @BeforeEach
    fun setUp() {
        routineProvider = mockk(relaxed = true)
        addSelectedDayToActivityUseCase = AddSelectedDayToActivityUseCase(routineProvider)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke adds selected day to activity and updates routine`() = runBlocking {
        // Given
        val activityToUpdate = ROUTINE.activities[0]
        val dayToAdd = "Sa"
        val updatedDays = activityToUpdate.days + dayToAdd
        val updatedActivity = activityToUpdate.copy(days = updatedDays)
        val updatedActivities = ROUTINE.activities.toMutableList().apply {
            this[0] = updatedActivity
        }
        val updatedRoutine = ROUTINE.copy(activities = updatedActivities)

        coEvery { routineProvider.getRoutine() } returns ROUTINE
        coEvery { routineProvider.addRoutine(any()) } answers { }

        // When
        addSelectedDayToActivityUseCase.invoke(activityToUpdate, dayToAdd)

        // Then
        coVerify(exactly = 1) { routineProvider.getRoutine() }


        val capturedRoutine = slot<Routine>()
        coVerify(exactly = 1) { routineProvider.addRoutine(capture(capturedRoutine)) }

        val capturedActivity = capturedRoutine.captured.activities.find { it.name == activityToUpdate.name }
        assertEquals(updatedDays, capturedActivity!!.days)
    }
}