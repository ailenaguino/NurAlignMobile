package com.losrobotines.nuralign.feature_home.presentation.utils

import com.losrobotines.nuralign.R

sealed class HomeItemData(
    val index: Int,
    val name: String,
    val image: Int
) {
    data object Mood: HomeItemData(0, "Estados de ánimo", R.drawable.icono_home_funcionalidades)
    data object Medication: HomeItemData(1, "Medicación", R.drawable.icono_home_funcionalidades)
    data object Sleep: HomeItemData(2, "Sueño", R.drawable.icono_home_funcionalidades)
    data object Therapy: HomeItemData(3, "Terapia", R.drawable.icono_home_funcionalidades)
    data object WeeklySummary: HomeItemData(4, "Resumen semanal", R.drawable.icono_home_funcionalidades)
    data object Companion: HomeItemData(5, "Mi acompañante", R.drawable.icono_home_funcionalidades)
    data object Achievement: HomeItemData(6, "Mis logros", R.drawable.icono_home_funcionalidades)
    data object Routine: HomeItemData(7, "Mi rutina", R.drawable.icono_home_funcionalidades)
}

