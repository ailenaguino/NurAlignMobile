package com.losrobotines.nuralign.feature_home.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Analytics
import androidx.compose.material.icons.twotone.AssignmentInd
import androidx.compose.material.icons.twotone.Bedtime
import androidx.compose.material.icons.twotone.ContentPaste
import androidx.compose.material.icons.twotone.CrueltyFree
import androidx.compose.material.icons.twotone.Diamond
import androidx.compose.material.icons.twotone.Medication
import androidx.compose.material.icons.twotone.Mood
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.losrobotines.nuralign.ui.theme.green
import com.losrobotines.nuralign.ui.theme.petroleum
import com.losrobotines.nuralign.ui.theme.pink
import com.losrobotines.nuralign.ui.theme.purple
import com.losrobotines.nuralign.ui.theme.secondaryColor
import com.losrobotines.nuralign.ui.theme.turquoise

sealed class HomeItemData(
    val index: Int,
    val name: String,
    val image: ImageVector,
    val color: Color,
    val desc: String
) {
    data object Mood: HomeItemData(0, "Estados de ánimo", Icons.TwoTone.Mood, turquoise, "Esta sección te va a permitir hacer un seguimiento de tu estado de ánimo según el nivel de cuatro características principales: ánimo elevado, depresivo, irritable o ansioso.")
    data object Medication: HomeItemData(1, "Medicación", Icons.TwoTone.Medication, green, "Esta sección te va a permitir seguir diariamente la toma de tus medicaciones.")
    data object Sleep: HomeItemData(2, "Sueño", Icons.TwoTone.Bedtime, purple, "Esta sección se encarga de crear un historial de cómo fue tu calidad de sueño cada noche.")
    data object Therapy: HomeItemData(3, "Terapia", Icons.TwoTone.AssignmentInd, pink, "Este apartado se ocupa de crear una agenda con todos tus terapeutas y sus respectivas sesiones.")
    data object WeeklySummary: HomeItemData(4, "Resumen semanal", Icons.TwoTone.Analytics, petroleum, "Esta sección te mostrará un resumen semanal de lo que fuiste completando día a día.")
    data object Companion: HomeItemData(5, "Mi acompañante", Icons.TwoTone.CrueltyFree, petroleum,"Esta opción te sirve para personalizar a tu acompañante (yo, Robotín), como más te guste.")
    data object Achievement: HomeItemData(6, "Mis logros", Icons.TwoTone.Diamond, petroleum,"Esta sección te mostrará los logros que fuiste ganando a lo largo de los días.")
    data object Routine: HomeItemData(7, "Mi rutina", Icons.TwoTone.ContentPaste, petroleum,"Este apartado esta dedicado a que lo completes con tu rutina, así puedo conocerte mejor.")
}

