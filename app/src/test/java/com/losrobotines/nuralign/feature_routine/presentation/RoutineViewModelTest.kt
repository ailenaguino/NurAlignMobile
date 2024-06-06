package com.losrobotines.nuralign.feature_routine.presentation
/*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.base.Verify.verify
import com.losrobotines.nuralign.feature_routine.data.database.RoutineEntity
import com.losrobotines.nuralign.feature_routine.domain.notification.Notification
import com.losrobotines.nuralign.feature_routine.domain.usescases.LoadRoutineUseCase
import com.losrobotines.nuralign.feature_routine.domain.usescases.SaveRoutineUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule
@OptIn(ExperimentalCoroutinesApi::class)
class RoutineViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @RelaxedMockK
    private lateinit var loadRoutineUseCase: LoadRoutineUseCase
    @RelaxedMockK
    private lateinit var saveRoutineUseCase: SaveRoutineUseCase
    @RelaxedMockK
    private lateinit var notification: Notification
    @RelaxedMockK
    private lateinit var routineViewModel: RoutineViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        loadRoutineUseCase = mockk()
        saveRoutineUseCase = mockk()
        notification = mockk()

        routineViewModel = RoutineViewModel(loadRoutineUseCase, saveRoutineUseCase, notification)
    }

    @Test
    fun `loadInitialRoutine sets correct values`() = runBlocking{
        // Given
        val mockRoutine = RoutineEntity(
            id = 1,
            sleepTime = "22:30",
            activity = "Gimnasio",
            activityTime = "20:00",
            activityDays = listOf("Lu", "Mi", "Vi")
        )
        coEvery { loadRoutineUseCase.execute() } returns mockRoutine

        // When
        routineViewModel.loadInitialRoutine()

        // Then
        assertEquals(mockRoutine.sleepTime, routineViewModel.bedTimeRoutine.value)
    }

    @Test
    fun `saveRoutine calls saveRoutineUseCase and sets isSaved to true`() = runBlocking {
        // Given
        coEvery { saveRoutineUseCase.execute(any()) } returns Unit

        // When
        routineViewModel.setSleepTimeRoutine("22:30")
        routineViewModel.setActivity("Gimnasio")
        routineViewModel.setActivityRoutine("20:00")
        routineViewModel.selectedDays.addAll(listOf("Lu", "Mi", "Vi"))

        routineViewModel.saveRoutine()

        // Then
        coVerify(exactly = 1) { saveRoutineUseCase.execute(any()) }
        assert(routineViewModel.isSaved.value == true)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
*/