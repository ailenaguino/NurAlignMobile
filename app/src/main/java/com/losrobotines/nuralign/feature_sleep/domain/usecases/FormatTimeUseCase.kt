package com.losrobotines.nuralign.feature_sleep.domain.usecases

class FormatTimeUseCase {
    fun removeColonFromTime(time: String): String {
        return time.replace(":", "")
    }

    fun addColonToTime(time: String): String {
        return time.chunked(2).joinToString(":")
    }
}