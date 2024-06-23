package com.losrobotines.nuralign.feature_achievements.presentation.screens

import android.content.Context
import com.losrobotines.nuralign.feature_achievements.domain.usecases.AddOneToCounterUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.FormatCorrectAchievementAndMessageUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.GetCounterUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.GetUserAchievementsUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.RestartCountersUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.StartCounterUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Test

class AchievementsViewModelTest {

    private val mood_tracker = AchievementsViewModel.TrackerConstants.MOOD_TRACKER
    private val mContextMock = mockk<Context>(relaxed = true)
    private val messageOK =
        "¡Felicitaciones! Conseguiste el logro Ánimo de Plata, vamos por buen camino."


    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: AchievementsViewModel
    private lateinit var getCounterUseCase: GetCounterUseCase
    private lateinit var startCounterUseCase: StartCounterUseCase
    private lateinit var addOneToCounterUseCase: AddOneToCounterUseCase
    private lateinit var formatCorrectAchievementAndMessageUseCase: FormatCorrectAchievementAndMessageUseCase
    private lateinit var restartCountersUseCase: RestartCountersUseCase
    private lateinit var getUserAchievementsUseCase: GetUserAchievementsUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCounterUseCase = mockk(relaxed = true)
        startCounterUseCase = mockk(relaxed = true)
        addOneToCounterUseCase = mockk(relaxed = true)
        formatCorrectAchievementAndMessageUseCase = mockk(relaxed = true)
        restartCountersUseCase = mockk(relaxed = true)
        getUserAchievementsUseCase = mockk(relaxed = true)
        viewModel = AchievementsViewModel(
            getCounterUseCase,
            startCounterUseCase,
            addOneToCounterUseCase,
            formatCorrectAchievementAndMessageUseCase,
            restartCountersUseCase,
            getUserAchievementsUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        clearAllMocks()
    }

    @Test
    fun `given counter null then start counter`() = runBlocking {
        coEvery { getCounterUseCase(mood_tracker) } returns null

        viewModel.trackerIsSaved(mContextMock, mood_tracker)

        coVerify { startCounterUseCase(mood_tracker) }

    }

    @Test
    fun `given an existent counter then add one to counter`() = runBlocking {
        coEvery { getCounterUseCase(mood_tracker) } returns 3

        viewModel.trackerIsSaved(mContextMock, mood_tracker)

        coVerify { addOneToCounterUseCase(mood_tracker) }

    }
}