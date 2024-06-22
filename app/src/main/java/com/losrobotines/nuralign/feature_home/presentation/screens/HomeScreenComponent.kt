package com.losrobotines.nuralign.feature_home.presentation.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.feature_home.presentation.utils.HomeItemData
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.FEMENINO
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.MASCULINO
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.USER_NAME
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.USER_SEX
import com.losrobotines.nuralign.feature_routine.domain.notification.Notification
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.preferences.PreferencesManager
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

private const val FIRST_TIME = "first_time"
@Composable
fun HomeScreenComponent(navController: NavController) {
    val homeItemsListTrackers = listOf(
        HomeItemData.Mood,
        HomeItemData.Medication,
        HomeItemData.Sleep,
        HomeItemData.Therapy
    )

    val homeItemsListOthers = listOf(
        HomeItemData.WeeklySummary,
        HomeItemData.Companion,
        HomeItemData.Achievement,
        HomeItemData.Routine
    )
    val context = LocalContext.current
    val notification = Notification()
    val preferencesManager = remember { PreferencesManager(context) }

    if (preferencesManager.getBoolean(FIRST_TIME, true)) {
        preferencesManager.saveBooleanData(FIRST_TIME, false)
        context.startActivity(Intent(context, FirstTimeActivity::class.java))
    } else {
        val name = preferencesManager.getString(USER_NAME, "Usuario")
        val sex = preferencesManager.getString(USER_SEX, MASCULINO)
        val saludo = when(sex){
            FEMENINO -> "Bienvenida"
            MASCULINO -> "Bienvenido"
            else -> "Bienvenidx"
        }
        Column() {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1)
            ) {
                item {
                    SharedComponents().HalfCircleTop(title = "$saludo $name")
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
                                "¡Hola $name! ¿Cómo estás hoy?",
                                "Clickeame para esconder mi diálogo"
                            )
                        )
                    }
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                item{
                    Text(text = "Seguimientos:", fontSize = 18.sp, color = secondaryColor, modifier = Modifier.padding(4.dp))
                }
                item{}
                items(homeItemsListTrackers.size) { item ->
                    HomeCardItem(homeItemsListTrackers[item], navController)
                }
                item{
                    Text(text = "Otros:", fontSize = 18.sp, color = secondaryColor, modifier = Modifier.padding(4.dp))
                }
                item{}
                items(homeItemsListOthers.size) { item ->
                    HomeCardItem(homeItemsListOthers[item], navController)
                }
            }
        }
    }
}

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
                    navController.navigate(Routes.MedicationTrackerScreen.route)
                }

                2 -> {
                    navController.navigate(Routes.SleepTrackerScreen.route)
                }

                3 -> {
                    navController.navigate(Routes.AddTherapyScreen.route)
                }
                //*************************************************************
                4 ->{
                    navController.navigate(Routes.TestGraficos.route)
                }
                //*********************************************

                6 -> {
                    navController.navigate(Routes.AchievementsScreen.route)
                }

                7 -> {
                    navController.navigate(Routes.RoutineScreen.route)
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
                tint = homeItemData.color,
                modifier = Modifier
                    .size(60.dp)
                    .padding(8.dp)
            )
            Text(
                text = homeItemData.name,
                color = homeItemData.color,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }

    }
}
