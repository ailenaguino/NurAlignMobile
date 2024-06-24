package com.losrobotines.nuralign.feature_sleep.domain.usecases

import javax.inject.Inject

class FormatTimeUseCase @Inject constructor() {
    fun removeColonFromTime(time: String): String {
        return time.replace(":", "")
    }

    fun addColonToTime(time: String): String {
        return if(time.length == 3) StringBuilder(time).insert(1, ":").toString() else time.chunked(2).joinToString(":")
    }
}