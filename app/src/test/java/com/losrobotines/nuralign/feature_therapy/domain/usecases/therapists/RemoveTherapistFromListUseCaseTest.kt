package com.losrobotines.nuralign.feature_therapy.domain.usecases.therapists

import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapistProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val THERAPIST_ID = 2.toShort()
private val THERAPIST = TherapistInfo(THERAPIST_ID, 1, "Will", "Sco", "email", 111234432, "N")

class RemoveTherapistFromListUseCaseTest {
    private lateinit var therapistProvider: TherapistProvider
    private lateinit var useCase: RemoveTherapistFromListUseCase
    private lateinit var therapistList: List<TherapistInfo>


    @BeforeEach
    fun setUp() {
        therapistProvider = mockk(relaxed = true)
        useCase = RemoveTherapistFromListUseCase(therapistProvider)
        therapistList = listOf(THERAPIST)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a therapist is removed from the list, it is removed from the provider`() =
        runBlocking {
            coEvery { therapistProvider.getTherapistList(any()) } returns therapistList

            val result = useCase.invoke(1, THERAPIST)

            coVerify { therapistProvider.deleteTherapistInfo(1, THERAPIST.therapistId!!) }
            assertTrue(result.isSuccess)

            coEvery { therapistProvider.getTherapistList(any()) } returns emptyList()
            val updatedList = therapistProvider.getTherapistList(1)
            assertTrue(updatedList.isEmpty())
        }
}