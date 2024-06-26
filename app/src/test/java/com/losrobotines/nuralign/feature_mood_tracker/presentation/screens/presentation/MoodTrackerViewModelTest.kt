package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation

import android.content.Context
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.losrobotines.nuralign.feature_achievements.domain.usecases.TrackerIsSavedUseCase
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsViewModel
import com.losrobotines.nuralign.feature_home.domain.usecases.CheckNextTrackerToBeCompletedUseCase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases.GetMoodTrackerInfoByDateUseCase
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases.SaveMoodTrackerDataUseCase
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.usecases.UpdateMoodTrackerUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class MoodTrackerViewModelTest {

    private val id = 1
    private val date = "2024-06-01"
    private val tracker = MoodTrackerInfo(id.toShort(), date, "1", "1", "", "", "1", "", "1", "")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var saveMoodTrackerDataUseCase: SaveMoodTrackerDataUseCase
    private lateinit var getMoodTrackerInfoByDateUseCase: GetMoodTrackerInfoByDateUseCase
    private lateinit var authRepository: AuthRepository
    private lateinit var service: UserService
    private lateinit var checkNextTrackerToBeCompletedUseCase: CheckNextTrackerToBeCompletedUseCase
    private lateinit var updateMoodTrackerUseCase: UpdateMoodTrackerUseCase
    private lateinit var viewModel: MoodTrackerViewModel
    private lateinit var newTracker: MoodTrackerInfo

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        saveMoodTrackerDataUseCase = mockk(relaxed = true)
        getMoodTrackerInfoByDateUseCase = mockk(relaxed = true)
        authRepository = mockk(relaxed = true)
        service = mockk(relaxed = true)
        checkNextTrackerToBeCompletedUseCase = mockk(relaxed = true)
        updateMoodTrackerUseCase = mockk(relaxed = true)
        val trackerIsSavedUseCase: TrackerIsSavedUseCase = mockk(relaxed = true)
        viewModel = MoodTrackerViewModel(
            saveMoodTrackerDataUseCase,
            getMoodTrackerInfoByDateUseCase,
            authRepository,
            service,
            checkNextTrackerToBeCompletedUseCase,
            updateMoodTrackerUseCase,
            trackerIsSavedUseCase
        )
        newTracker = mockk(relaxed = true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        clearAllMocks()
    }

    @Disabled
    @Test
    fun `if tracker doesn't exists then save`() = runBlocking {
        mockkStatic(Looper::class)
        val looper = mockk<Looper> {
            every { thread } returns Thread.currentThread()
        }
        val mContextMock = mockk<Context>(relaxed = true)
        every { Looper.getMainLooper() } returns looper
        coEvery { getMoodTrackerInfoByDateUseCase(id, date) } returns null
        viewModel.saveData(mContextMock)
        coVerify { saveMoodTrackerDataUseCase(tracker) }
    }

    @Disabled
    @Test
    fun `if tracker exists then update`() = runBlocking {
        mockkStatic(Looper::class)
        val looper = mockk<Looper> {
            every { thread } returns Thread.currentThread()
        }
        val mContextMock = mockk<Context>(relaxed = true)
        every { Looper.getMainLooper() } returns looper
        coEvery { getMoodTrackerInfoByDateUseCase(id, date) } returns tracker
        viewModel.saveData(mContextMock)
        coVerify { updateMoodTrackerUseCase(newTracker) }
    }
}