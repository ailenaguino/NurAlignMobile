package com.losrobotines.nuralign.feature_achievements.presentation.utils

import com.losrobotines.nuralign.R

sealed class AchievementsData(
    val image: Int,
    val name: String,
    val descr: String
) {
    data object animoBronce: AchievementsData (R.drawable.animo_bronce, "Ánimo de bronce", "Completá tu estado de ánimo por primera vez.")
    data object animoPlata: AchievementsData (R.drawable.animo_plata, "Ánimo de plata", "Completá tu estado de ánimo siete veces.")
    data object animoOro: AchievementsData (R.drawable.animo_oro, "Ánimo de oro", "Completá tu estado de ánimo treinta veces.")
    data object suenioBronce: AchievementsData (R.drawable.suenio_bronce, "Sueño de bronce", "Completá tu seguimiento del sueño por primera vez.")
    data object suenioPlata: AchievementsData (R.drawable.suenio_plata, "Sueño de plata", "Completá tu seguimiento del sueño siete veces.")
    data object suenioOro: AchievementsData (R.drawable.suenio_oro, "Sueño de oro", "Completá tu seguimiento del sueño treinta veces.")
    data object medicacionBronce: AchievementsData (R.drawable.medicacion_bronce, "Medicina de bronce", "Completá tu seguimiento de medicación por primera vez.")
    data object medicacionPlata: AchievementsData (R.drawable.medicacion_plata, "Medicina de plata", "Completá tu seguimiento de medicación siete veces.")
    data object medicacionOro: AchievementsData (R.drawable.medicacion_oro, "Medicina de oro", "Completá tu seguimiento de medicación treinta veces.")
    data object terapiaBronce: AchievementsData (R.drawable.terapia_bronce, "Terapia de bronce", "Completá por primera vez una sesión de terapia.")
    data object terapiaPlata: AchievementsData (R.drawable.terapia_plata, "Terapia de plata", "Completá siete sesiones de terapia.")
    data object terapiaOro: AchievementsData (R.drawable.terapia_oro, "Terapia de oro", "Completá quince sesiones de terapia.")
}

