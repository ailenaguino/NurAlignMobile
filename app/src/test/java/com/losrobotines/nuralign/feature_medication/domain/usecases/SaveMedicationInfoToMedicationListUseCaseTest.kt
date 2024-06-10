package com.losrobotines.nuralign.feature_medication.domain.usecases

import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private val MEDICATION_A = MedicationInfo(9, "A", 200, "Y")

class SaveMedicationInfoToMedicationListUseCaseTest {

    private lateinit var saveMedUseCase: AddNewMedicationToListUseCase
    private lateinit var emptyList: MutableList<MedicationInfo?>
    private lateinit var listWithMedicationInfoItem: MutableList<MedicationInfo?>

    @BeforeEach
    fun setUp() {
        saveMedUseCase = AddNewMedicationToListUseCase()
        emptyList = mutableListOf()
        listWithMedicationInfoItem = mutableListOf(MEDICATION_A)
    }

    @Test
    fun `when medication info isn't in a medication list, then add it and return it`() {
        val result = saveMedUseCase.invoke(MEDICATION_A, emptyList)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `when medication info is in a medication list, then don't add it`() {
        val result = saveMedUseCase.invoke(MEDICATION_A, listWithMedicationInfoItem)

        assertTrue(result.isFailure)
    }
}