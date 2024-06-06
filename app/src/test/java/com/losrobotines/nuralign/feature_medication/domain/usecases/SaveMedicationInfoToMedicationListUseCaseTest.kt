package com.losrobotines.nuralign.feature_medication.domain.usecases

import android.util.Log
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val MEDICATION_A = MedicationInfo(9, "A", 200, "Y")

class SaveMedicationInfoToMedicationListUseCaseTest {

    private lateinit var useCase: SaveMedicationInfoToMedicationListUseCase
    private lateinit var emptyList: MutableList<MedicationInfo?>
    private lateinit var listWithMedicationInfoItem: MutableList<MedicationInfo?>

    @BeforeEach
    fun setUp() {
        useCase = SaveMedicationInfoToMedicationListUseCase()
        emptyList = mutableListOf()
        listWithMedicationInfoItem = mutableListOf(MEDICATION_A)
    }

    @Test
    fun `when medication info isn't in a medication list, then add it and return it`() {
        val result = useCase.invoke(MEDICATION_A, emptyList)

        assertEquals(listWithMedicationInfoItem, result)
    }

    @Test
    fun `when medication info is in a medication list, then don't add it`() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        val result = useCase.invoke(MEDICATION_A, listWithMedicationInfoItem)

        assertEquals(listWithMedicationInfoItem, result)
    }
}