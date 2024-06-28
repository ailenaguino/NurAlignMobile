package com.losrobotines.nuralign.feature_medication.domain.usecases.tracker

import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationTrackerProvider
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class CheckIfMedicationTrackerWasCompletedUseCase @Inject constructor(private val medicationTrackerProvider: MedicationTrackerProvider) {
    suspend operator fun invoke(domainId: Int): Boolean {
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val result = medicationTrackerProvider.getMedicationTrackerData(domainId.toShort(), date)
        return (result.getOrNull() != null)
    }
}