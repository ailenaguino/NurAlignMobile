package com.losrobotines.nuralign.feature_therapy.domain.usecases.therapy_session

import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapySessionProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val THERAPY_SESSION =
    TherapySessionInfo(1, 1, 1, "2024-06-29", 1300, "Notas pre sesion", "", "")

class SaveTherapySessionUseCaseTest {
    private lateinit var therapySessionProvider: TherapySessionProvider
    private lateinit var useCase: SaveTherapySessionUseCase
    private lateinit var therapySessionList: List<TherapySessionInfo>

    @BeforeEach
    fun setUp() {
        therapySessionProvider = mockk(relaxed = true)
        useCase = SaveTherapySessionUseCase(therapySessionProvider)
        therapySessionList = listOf()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a therapy session is provided then save it`() = runBlocking {
        coEvery {
            therapySessionProvider.getTherapySessionList(
                any(),
                any()
            )
        } returns therapySessionList

        val result = useCase.invoke(THERAPY_SESSION)
        coVerify { therapySessionProvider.saveTherapySessionInfo(any()) }
        assertTrue(result.isSuccess)

        coEvery { therapySessionProvider.getTherapySessionList(any(), any()) } returns listOf(
            THERAPY_SESSION
        )
        val sessionList = therapySessionProvider.getTherapySessionList(1, 1)
        assertTrue(sessionList.contains(THERAPY_SESSION))
    }
}