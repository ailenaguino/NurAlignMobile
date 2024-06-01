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
import androidx.compose.ui.graphics.vector.ImageVector
import com.losrobotines.nuralign.R

sealed class HomeItemData(
    val index: Int,
    val name: String,
    val image: ImageVector
) {
    data object Mood: HomeItemData(0, "Estados de ánimo", Icons.TwoTone.Mood)
    data object Medication: HomeItemData(1, "Medicación", Icons.TwoTone.Medication)
    data object Sleep: HomeItemData(2, "Sueño", Icons.TwoTone.Bedtime)
    data object Therapy: HomeItemData(3, "Terapia", Icons.TwoTone.AssignmentInd)
    data object WeeklySummary: HomeItemData(4, "Resumen semanal", Icons.TwoTone.Analytics)
    data object Companion: HomeItemData(5, "Mi acompañante", Icons.TwoTone.CrueltyFree)
    data object Achievement: HomeItemData(6, "Mis logros", Icons.TwoTone.Diamond)
    data object Routine: HomeItemData(7, "Mi rutina", Icons.TwoTone.ContentPaste)
}

