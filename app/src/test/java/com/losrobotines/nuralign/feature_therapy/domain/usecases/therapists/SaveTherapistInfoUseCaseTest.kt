package com.losrobotines.nuralign.feature_therapy.domain.usecases.therapists

import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapistProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val THERAPIST =
    TherapistInfo(2, 1, "William", "Scottman", "wscottman@gmail.com", 1112344321, "N")

class SaveTherapistInfoUseCaseTest {
    private lateinit var therapistProvider: TherapistProvider
    private lateinit var useCase: SaveTherapistInfoUseCase
    private lateinit var therapistList: List<TherapistInfo>

    @BeforeEach
    fun setUp() {
        therapistProvider = mockk(relaxed = true)
        useCase = SaveTherapistInfoUseCase(therapistProvider)
        therapistList = listOf()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a therapist is saved then the therapist list is updated`() = runBlocking {
        coEvery { therapistProvider.getTherapistList(any()) } returns therapistList

        val result = useCase.invoke(THERAPIST)

        coVerify { therapistProvider.saveTherapistInfo(THERAPIST) }
        assertTrue(result.isSuccess)

        coEvery { therapistProvider.getTherapistList(any()) } returns listOf(THERAPIST)
        val updatedList = therapistProvider.getTherapistList(1)
        assertTrue(updatedList.isNotEmpty())
    }
}
