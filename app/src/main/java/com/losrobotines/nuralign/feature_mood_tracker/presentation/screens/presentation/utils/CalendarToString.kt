package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation.utils

fun getDayOfWeek(day: Int) : String{
    return when(day){
        1-> "Domingo"
        2-> "Lunes"
        3-> "Martes"
        4-> "Miércoles"
        5-> "Jueves"
        6-> "Viernes"
        else-> "Sábado"
    }
}

fun getMonth(month: Int) : String{
    return when(month){
        0-> "Enero"
        1-> "Febrero"
        2-> "Marzo"
        3-> "Abril"
        4-> "Mayo"
        5-> "Junio"
        6-> "Julio"
        7-> "Agosto"
        8-> "Septiembre"
        9-> "Octubre"
        10-> "Noviembre"
        else-> "Diciembre"
    }
}