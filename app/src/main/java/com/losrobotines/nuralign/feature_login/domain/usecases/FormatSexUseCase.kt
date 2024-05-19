package com.losrobotines.nuralign.feature_login.domain.usecases

import javax.inject.Inject

class FormatSexUseCase @Inject constructor(){
    operator fun invoke(sex: String): String {
        return when(sex){
            "Femenino" -> "F"
            "Masculino" -> "M"
            else -> "X"
        }
    }
}