package com.losrobotines.nuralign.feature_weekly_summary.domain.usecases

import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import javax.inject.Inject

class GetWeeklyMedicationInfoUseCase @Inject constructor(
    private val weeklySummaryProvider: WeeklySummaryProvider
) {

}