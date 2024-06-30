package com.losrobotines.nuralign.feature_therapy.domain.usecases.therapy_session

import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapySessionProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val THERAPY_SESSION =
    TherapySessionInfo(1, 1, 1, "2024-06-29", 1300, "Notas pre sesion", "", "")
private val NEW_THERAPY_SESSION =
    THERAPY_SESSION.copy(postSessionNotes = "Notas post", sessionFeel = "3")


class EditTherapySessionUseCaseTest {
    private lateinit var therapySessionProvider: TherapySessionProvider
    private lateinit var useCase: EditTherapySessionUseCase
    private lateinit var therapySessionInfo: TherapySessionInfo

    @BeforeEach
    fun setUp() {
        therapySessionProvider = mockk(relaxed = true)
        useCase = EditTherapySessionUseCase(therapySessionProvider)
        therapySessionInfo = THERAPY_SESSION
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a therapy session is provided then edit it`() = runBlocking {
        coEvery { therapySessionProvider.getTherapySessionInfo(any()) } returns therapySessionInfo

        val result = useCase.invoke(NEW_THERAPY_SESSION)
        coVerify { therapySessionProvider.updateTherapySessionInfo(NEW_THERAPY_SESSION) }
        assertTrue(result.isSuccess)

        coEvery { therapySessionProvider.getTherapySessionInfo(any()) } returns NEW_THERAPY_SESSION
        val updatedSession = therapySessionProvider.getTherapySessionInfo(1)
        assertEquals(NEW_THERAPY_SESSION, updatedSession)
    }
}