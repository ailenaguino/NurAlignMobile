package com.losrobotines.nuralign.feature_routine.presentation

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.losrobotines.nuralign.feature_routine.domain.models.Activity
import com.losrobotines.nuralign.feature_routine.domain.models.Routine
import com.losrobotines.nuralign.feature_routine.domain.notification.Notification
import com.losrobotines.nuralign.feature_routine.domain.usescases.ActivityProviderUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.LoadRoutineUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.SaveRoutineUseCase
import com.losrobotines.nuralign.gemini.GeminiContentGenerator
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
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
class RoutineViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var loadRoutineUseCase: LoadRoutineUseCase
    private lateinit var saveRoutineUseCase: SaveRoutineUseCase
    private lateinit var geminiContentGenerator: GeminiContentGenerator
    private lateinit var notification: Notification
    private lateinit var activityProviderUseCase: ActivityProviderUseCase
    private lateinit var routineViewModel: RoutineViewModel


    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loadRoutineUseCase = mockk(relaxed = true)
        saveRoutineUseCase = mockk(relaxed = true)
        geminiContentGenerator = mockk(relaxed = true)
        activityProviderUseCase=mockk(relaxed = true)
        notification = mockk(relaxed = true)

        routineViewModel = RoutineViewModel(
            loadRoutineUseCase,
            saveRoutineUseCase,
            geminiContentGenerator,
            activityProviderUseCase,
            notification
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `loadInitialRoutine updates LiveData values`() = runBlocking {
        mockkStatic(Looper::class)
        val looper = mockk<Looper> {
            every { thread } returns Thread.currentThread()
        }

        every { Looper.getMainLooper() } returns looper
        // Given
        coEvery { loadRoutineUseCase.invoke() } returns ROUTINE

        // When
        routineViewModel.loadInitialRoutine()

        // Then
        Assert.assertEquals(ROUTINE.sleepTime, routineViewModel.bedTimeRoutine.value)

    }
}