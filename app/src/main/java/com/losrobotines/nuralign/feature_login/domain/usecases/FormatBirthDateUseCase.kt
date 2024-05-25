package com.losrobotines.nuralign.feature_login.domain.usecases

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FormatBirthDateUseCase @Inject constructor() {
    @SuppressLint("NewApi")
    operator fun invoke(date: String): String? {
        if (date.matches(Regex("\\d{8}"))) {
            val formatoOriginal = DateTimeFormatter.ofPattern("ddMMyyyy")
            val fechaLocalDate = LocalDate.parse(date, formatoOriginal)
            val nuevoFormato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return fechaLocalDate.format(nuevoFormato)
        }else{return null}
    }
}