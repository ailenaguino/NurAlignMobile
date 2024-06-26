package com.losrobotines.nuralign.feature_resumen_semanal.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class CalculateAverageSleepHoursUseCase @Inject constructor() {

    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(sleepInfoList: List<SleepInfo?>): Double {
        if (sleepInfoList.isEmpty()) return 0.0

        var totalHours = 0
        var count = 0

        for (sleepInfo in sleepInfoList) {
            sleepInfo?.let {
                totalHours += it.sleepHours
                count++
            }
        }

        return if (count == 0) 0.0 else totalHours.toDouble() / count
    }
}