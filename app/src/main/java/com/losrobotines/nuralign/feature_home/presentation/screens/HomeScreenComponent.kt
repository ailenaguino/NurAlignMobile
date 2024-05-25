package com.losrobotines.nuralign.feature_home.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_home.presentation.utils.HomeItemData
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents

@Composable
fun HomeScreenComponent(navController: NavController) {
    val homeItemsList = listOf(
        HomeItemData.Mood,
        HomeItemData.Medication,
        HomeItemData.Sleep,
        HomeItemData.Therapy,
        HomeItemData.WeeklySummary,
        HomeItemData.Companion,
        HomeItemData.Achievement,
        HomeItemData.Routine
    )

    Column() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1)
        ) {
            item {
                SharedComponents().HalfCircleTop(title = "Bienvenido Carlos")
            }
            item {
                Box(modifier = Modifier.padding(top = 8.dp)) {
                    SharedComponents().CompanionTextBalloon("¡Hola! ¿Cómo estás hoy?")
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(homeItemsList.size) { item ->
                HomeItem(homeItemsList[item], navController)
            }
        }
    }
}


@Composable
private fun HomeItem(homeItemData: HomeItemData, navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Image(
                painterResource(id = homeItemData.image),
                contentDescription = "Fondo",
                modifier = Modifier
                    .size(90.dp)
                    .clickable {
                        when (homeItemData.index) {
                            0 -> {
                                navController.navigate(Routes.MoodTrackerScreen.route)
                            }

                            1 -> {
                                navController.navigate(Routes.AddMedicationScreen.route)
                            }

                            2 -> {
                                navController.navigate(Routes.SleepTrackerScreen.route)
                            }

                            3 -> {
                                navController.navigate(Routes.AddTherapyScreen.route)
                            }

                            6 -> {
                                navController.navigate(Routes.AchievementsScreen.route)
                            }
                        }
                    }
            )
        }
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = homeItemData.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}