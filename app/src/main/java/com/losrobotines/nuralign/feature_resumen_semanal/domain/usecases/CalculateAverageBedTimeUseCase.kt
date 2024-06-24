package com.losrobotines.nuralign.feature_resumen_semanal.domain.usecases

import android.annotation.SuppressLint
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class CalculateAverageBedTimeUseCase @Inject constructor() {
    @SuppressLint("DefaultLocale")
    operator fun invoke(sleepInfoList: List<SleepInfo?>): String {
        val nonNullSleepInfoList = sleepInfoList.filterNotNull()

        if (nonNullSleepInfoList.isEmpty()) {
            return "00:00"
        }

        val totalMinutes = nonNullSleepInfoList.sumBy { it.bedTime.toMinutesFromMidnight() }
        val averageMinutes = totalMinutes / nonNullSleepInfoList.size

        val hours = averageMinutes / 60
        val minutes = averageMinutes % 60

        return String.format("%02d:%02d", hours, minutes)
    }
}

fun String.toMinutesFromMidnight(): Int {
    return if (this.length == 4) {
        val hours = this.substring(0, 2).toInt()
        val minutes = this.substring(2, 4).toInt()
        hours * 60 + minutes
    } else {
        0
    }
}

