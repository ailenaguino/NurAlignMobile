package com.losrobotines.nuralign.feature_therapy.domain.usecases

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

private const val PATIENT_ID = 1.toShort()
private const val THERAPIST_ID = 2.toShort()
private val OLD_THERAPIST = TherapistInfo(THERAPIST_ID, "Will", "Sco", "email", 111234432, "N")
private const val NEW_NAME = "William"
private const val NEW_LAST_NAME = "Scottman"
private const val NEW_EMAIL = "wscottman@gmail.com"
private const val NEW_PHONE = 1112344321
private val NEW_THERAPIST =
    TherapistInfo(THERAPIST_ID, NEW_NAME, NEW_LAST_NAME, NEW_EMAIL, NEW_PHONE, "N")

class EditExistingTherapistInListUseCaseTest {
    private lateinit var therapistProvider: TherapistProvider
    private lateinit var useCase: EditExistingTherapistInListUseCase
    private lateinit var therapistList: List<TherapistInfo>

    @BeforeEach
    fun setUp() {
        therapistProvider = mockk(relaxed = true)
        therapistList = listOf(OLD_THERAPIST)
        useCase = EditExistingTherapistInListUseCase(therapistProvider)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when a therapist is edited, it is updated in the list`() = runBlocking {
        coEvery { therapistProvider.getTherapistList(any()) } returns therapistList

        val result = useCase.invoke(
            NEW_NAME,
            NEW_LAST_NAME,
            NEW_EMAIL,
            NEW_PHONE,
            OLD_THERAPIST,
            therapistList
        )

        coVerify { therapistProvider.updateTherapistInfo(NEW_THERAPIST) }
        assertTrue(result.isSuccess)

        coEvery { therapistProvider.getTherapistList(any()) } returns listOf(NEW_THERAPIST)
        val updatedList = therapistProvider.getTherapistList(PATIENT_ID)
        assertTrue(updatedList.contains(NEW_THERAPIST))
    }
}