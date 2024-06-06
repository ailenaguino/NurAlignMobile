package com.losrobotines.nuralign.feature_routine.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import com.losrobotines.nuralign.feature_routine.domain.gemini.GeminiContentGenerator
import com.losrobotines.nuralign.feature_routine.domain.notification.Notification
import com.losrobotines.nuralign.feature_routine.domain.usescases.LoadRoutineUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.SaveRoutineUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/*
class RoutineViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var loadRoutineUseCase: LoadRoutineUseCase
    @MockK
    private  lateinit var saveRoutineUseCase: SaveRoutineUseCase
    @MockK
    private  lateinit var geminiContentGenerator: GeminiContentGenerator
    @MockK
    private  lateinit var notification: Notification

    @BeforeEach
    fun setUp() {
        loadRoutineUseCase=mockk(relaxed = true)
        saveRoutineUseCase=mockk(relaxed = true)
        geminiContentGenerator=mockk(relaxed = true)
        notification=mockk(relaxed = true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadInitialRoutine should load routine data`() = runTest {
        Dispatchers.setMain(Dispatchers.Unconfined)
        // Given
        val routine = RoutineEntity(
            id = 1,
            sleepTime = "22:00",
            activity = "Gimnasio",
            activityTime = "06:00",
            activityDays = listOf("Lu", "Mi", "Vi")
        )
        coEvery { loadRoutineUseCase.execute() } returns routine
        val routineViewModel = RoutineViewModel(
            loadRoutineUseCase,
            saveRoutineUseCase,
            geminiContentGenerator,
            notification
        )
        // When
        routineViewModel.loadInitialRoutine()
        // Then
        assertEquals(routine.sleepTime, routineViewModel.bedTimeRoutine.value)
        assertEquals(routine.activity, routineViewModel.activity.value)
        assertEquals(routine.activityTime, routineViewModel.activityRoutineTime.value)
        assertTrue(routineViewModel.selectedDays.containsAll(routine.activityDays))
        assertTrue(routineViewModel.isSaved.value!!)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `saveRoutine should save routine data`() = runTest {
        Dispatchers.setMain(Dispatchers.Unconfined)
        // Given
        val routineViewModel = RoutineViewModel(
            loadRoutineUseCase,
            saveRoutineUseCase,
            geminiContentGenerator,
            notification
        )
        routineViewModel.setSleepTimeRoutine("22:00")
        routineViewModel.setActivity("Tenis")
        routineViewModel.setActivityRoutine("10:00")
        routineViewModel.addSelectedDay("Lu")
        routineViewModel.addSelectedDay("Mi")
        routineViewModel.addSelectedDay("Vi")

        // When
        routineViewModel.saveRoutine()

        // Then
        coEvery { saveRoutineUseCase.execute(any()) } returns Unit
        assertTrue(routineViewModel.isSaved.value!!)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `generateNotificationMessage should call geminiContentGenerator`() =
        runTest {
            Dispatchers.setMain(Dispatchers.Unconfined)
            // Given
            val prompt = "Test prompt"
            val generatedMessage = "Test message"
            coEvery { geminiContentGenerator.generateContent(prompt) } returns generatedMessage
            val routineViewModel =
                RoutineViewModel(
                    loadRoutineUseCase,
                    saveRoutineUseCase,
                    geminiContentGenerator,
                    notification
                )
            // When
            val result = routineViewModel.generateNotificationMessage(prompt)

            // Then
            assertEquals(generatedMessage, result)
        }


}

 */
