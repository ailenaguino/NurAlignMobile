package com.losrobotines.nuralign.feature_home.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.feature_home.presentation.utils.HomeItemData
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor

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

    var isVisible by remember { mutableStateOf(false) }

    Column() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1)
        ) {
            item {
                SharedComponents().HalfCircleTop(title = "Bienvenido Carlos")
            }
            item {
                LargeFloatingActionButton(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    containerColor = mainColor,
                    modifier = Modifier.padding(8.dp),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 7.dp
                    )
                ) {
                    SharedComponents().fabCompanion(
                        listOf(
                            "¡Hola! ¿Cómo estás hoy?",
                            "Clickeame para esconder mi diálogo"
                        )
                    )
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
                //HomeItem(homeItemsList[item], navController)
                HomeCardItem(homeItemsList[item], navController)
            }
            item{
                Button(onClick = {isVisible = true}) {
                    
                }
            }
        }

    }
    SharedComponents().companionCongratulation(isVisible = isVisible)
}

/*
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
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeCardItem(homeItemData: HomeItemData, navController: NavController) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .size(width = 90.dp, height = 120.dp)
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = {
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
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = homeItemData.image,
                contentDescription = "Seguimiento del ánimo",
                tint = mainColor,
                modifier = Modifier
                    .size(60.dp)
                    .padding(4.dp)
            )
            Text(text = homeItemData.name, color = mainColor, textAlign = TextAlign.Center, fontSize = 14.sp)
        }

    }
}