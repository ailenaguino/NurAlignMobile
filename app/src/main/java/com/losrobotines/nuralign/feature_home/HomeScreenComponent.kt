package com.losrobotines.nuralign.feature_home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.secondaryColor

@Composable
fun HomeScreenComponent(navController: NavController) {
    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        item {
            SharedComponents().HalfCircleTop(title = "Bienvenido Carlos")
        }
        item {
            Box(modifier = Modifier.height(85.dp).padding(top=8.dp)) {
                SharedComponents().CompanionTextBalloon("¡Hola! ¿Cómo estás hoy?")
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val nombres = listOf(
                    "Estados de\n   ánimo",
                    "Medicación",
                    "Sueño",
                    "Terapia",
                    "Resumen\n semanal",
                    "          Mi\nacompañante",
                    "   Mis\nLogros",
                    "    Mi\n Rutina"
                )
                Spacer(modifier = Modifier.height(8.dp))

                repeat(3) { rowIndex ->
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(3) { imageIndex ->
                            val nameIndex = rowIndex * 3 + imageIndex
                            Spacer(modifier = Modifier.width(5.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                val imageName = nombres.getOrElse(nameIndex) { "" }
                                Image(
                                    painterResource(id = R.drawable.icono_home_funcionalidades),
                                    contentDescription = "Fondo",
                                    modifier = Modifier
                                        .size(90.dp)
                                        .clickable {
                                            when (nameIndex) {
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

                                                8 -> {
                                                    navController.navigate(Routes.SettingsScreen.route)
                                                }

                                            }
                                        }
                                )
                                Text(
                                    text = imageName,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

