package com.losrobotines.nuralign.feature_login.domain.usecases

import com.losrobotines.nuralign.feature_login.presentation.screens.signup.FEMENINO
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.MASCULINO
import javax.inject.Inject

class FormatSexUseCase @Inject constructor(){
    operator fun invoke(sex: String): String {
        return when(sex){
            FEMENINO -> "FEMALE"
            MASCULINO -> "MALE"
            else -> "X"
        }
    }
}