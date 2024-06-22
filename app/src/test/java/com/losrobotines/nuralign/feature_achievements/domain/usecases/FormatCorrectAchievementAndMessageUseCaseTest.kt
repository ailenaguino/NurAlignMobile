package com.losrobotines.nuralign.feature_achievements.domain.usecases

import com.losrobotines.nuralign.feature_achievements.domain.models.Counter
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsViewModel
import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FormatCorrectAchievementAndMessageUseCaseTest {

    private val mood_tracker = AchievementsViewModel.TrackerConstants.MOOD_TRACKER
    private val messageOK = "¡Felicitaciones! Conseguiste el logro Ánimo de Oro, es increíble, mirá de todo lo que sos capaz."
    private lateinit var usecase: FormatCorrectAchievementAndMessageUseCase
    private lateinit var addAchievementUsecase: AddAchievementUseCase

    @BeforeEach
    fun setUp() {
        addAchievementUsecase = mockk(relaxed = true)
        usecase = FormatCorrectAchievementAndMessageUseCase(addAchievementUsecase)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `return the correct string depending on the counter passed`() = runBlocking{
        val messageResult = usecase(Counter(mood_tracker, 30))
        assertEquals(messageOK, messageResult)
    }

    @Test
    fun `return an empty string depending on the counter passed`() = runBlocking{
        val messageResult = usecase(Counter(mood_tracker, 12))
        assertEquals("", messageResult)
    }
}