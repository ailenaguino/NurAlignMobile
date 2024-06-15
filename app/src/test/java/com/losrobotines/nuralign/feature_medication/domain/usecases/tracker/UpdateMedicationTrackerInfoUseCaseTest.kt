package com.losrobotines.nuralign.feature_medication.domain.usecases.tracker

import android.util.Log
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationTrackerProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

private val MEDICATION_TRACKER_A = MedicationTrackerInfo(1, "2024-01-01", "N")
private val MEDICATION_TRACKER_B = MedicationTrackerInfo(1, "2024-01-01", "Y")

class UpdateMedicationTrackerInfoUseCaseTest {

    private lateinit var medicationTrackerProvider: MedicationTrackerProvider
    private lateinit var updateMedTrackerUseCase: UpdateMedicationTrackerInfoUseCase
    private lateinit var medicationTrackerList: List<MedicationTrackerInfo>
    private lateinit var medicationTrackerListWithdata: List<MedicationTrackerInfo>

    @BeforeEach
    fun setUp() {
        medicationTrackerProvider = mockk(relaxed = true)
        updateMedTrackerUseCase = UpdateMedicationTrackerInfoUseCase(medicationTrackerProvider)
        medicationTrackerList = listOf()
        medicationTrackerListWithdata = listOf(MEDICATION_TRACKER_A)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when takenFlag is changed to Y then update medication tracker`() = runBlocking {
        coEvery { updateMedTrackerUseCase(MEDICATION_TRACKER_B) } returns Result.success(
            MEDICATION_TRACKER_B
        )

        val result = updateMedTrackerUseCase(MEDICATION_TRACKER_B)

        assertTrue(result.isSuccess)
        coVerify { medicationTrackerProvider.updateMedicationTrackerData(MEDICATION_TRACKER_B) }
        assertEquals(MEDICATION_TRACKER_B, result.getOrNull())
    }

    @Disabled
    @Test
    fun `when medication tracker for a day already exists then update it`() = runBlocking { }

    @Disabled
    @Test
    fun `when medication tracker for non-existent medication is provided then return failure`() =
        runBlocking { }
}