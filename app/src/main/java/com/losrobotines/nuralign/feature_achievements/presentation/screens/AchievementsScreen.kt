package com.losrobotines.nuralign.feature_achievements.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_achievements.presentation.utils.AchievementsData
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.NurAlignTheme


@SuppressLint("NotConstructor")
@Composable
 fun AchievementsScreen(navController: NavController) {
    val achievementsList = listOf(
        AchievementsData("Ánimo de bronce", "Completá tu estado de ánimo por primera vez"),
        AchievementsData(
            "Ánimo de plata",
            "Completá tu estado de ánimo durante una semana consecutiva"
        ),
        AchievementsData("Ánimo de oro", "Completá tu estado de ánimo durante un mes consecutivo"),
        AchievementsData("Amigo virtual", "Chateá por primera vez con tu acompañante virtual"),
        AchievementsData("Rutinario", "Completá tu rutina"),
        AchievementsData("Medicación de bronce", "Completá tu toma de medicación por primera vez"),
        AchievementsData(
            "Medicación de plata",
            "Completá tu toma de medicación durante una semana consecutiva"
        ),
        AchievementsData(
            "Medicación de oro",
            "Completá tu toma de medicación durante un mes consecutivo"
        ),
        AchievementsData("Terapia de bronce", "Completá una sesión de terapia por primera vez"),
        AchievementsData("Terapia de plata", "Completá siete sesiones de terapia diferentes")
    )
    SharedComponents().HalfCircleTop("Logros")
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 170.dp)
    ) {
        items(achievementsList.size) { item ->
            Achievement(achievementsList[item])
        }
    }
}

@Composable
private fun Achievement(achievement: AchievementsData) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.CenterStart)
        {
            Image(
                painterResource(id = R.drawable.achievement_default),
                contentDescription = "Achievement ${achievement.name}",
                modifier = Modifier
                    .size(90.dp)
                    .clickable {
                        openAlertDialog = true
                    }
            )
            if (openAlertDialog) {
                AchievementAlertDialog(
                    onDismissRequest = { openAlertDialog = false },
                    dialogTitle = achievement.name,
                    dialogText = achievement.descr,
                    image = R.drawable.achievement_default
                )
            }
        }
    }
}

@Composable
private fun AchievementAlertDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    image: Int
) {
    AlertDialog(
        icon = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(start = 25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = image),
                        contentDescription = "Fondo",
                        modifier = Modifier
                            .size(85.dp)
                    )

                }
                Box(
                    modifier = Modifier.weight(0.1f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Icon(
                        Icons.Default.Close,
                        "close",
                        modifier = Modifier.clickable { onDismissRequest() })
                }
            }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {}
    )
}
