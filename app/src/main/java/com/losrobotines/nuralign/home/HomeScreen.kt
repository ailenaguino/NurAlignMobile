package com.losrobotines.nuralign.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsScreen
import com.losrobotines.nuralign.feature_medication.presentation.screens.AddMedicationScreen
import com.losrobotines.nuralign.feature_sleep.presentation.screens.SleepTrackerScreen
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import com.losrobotines.nuralign.ui.theme.secondaryColor
import com.losrobotines.nuralign.feature_mood_tracker_presentation_screens.MoodTrackerScreen
import com.losrobotines.nuralign.feature_settings.presentation.screens.SettingsScreen
import com.losrobotines.nuralign.feature_therapy.presentation.screens.AddTherapistScreen


class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurAlignTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current.applicationContext
                    BottomNavigationBar { Home(context) }
                }
            }
        }
    }

    @Composable
    fun Home(content: Context) {
        Image(
            painter = painterResource(id = com.losrobotines.nuralign.R.drawable.fondo),
            contentDescription = "Fondo",
            alignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡Bienvenido Roberto!",
                color = Color.White,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(top = 43.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .clickable {
                        Toast
                            .makeText(content, "Hola", Toast.LENGTH_SHORT)
                            .show()
                    }
            )
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
            Spacer(modifier = Modifier.height(70.dp))

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
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val imageName = nombres.getOrElse(nameIndex) { "" }
                            Log.d(
                                "Antes de haces clik",
                                "Nombre de la imagen: $imageName, Index: $nameIndex",
                            )
                            Image(
                                painterResource(id = R.drawable.icono_home_funcionalidades),
                                contentDescription = "Fondo",
                                modifier = Modifier
                                    .size(90.dp)
                                    .clickable {
                                        when (nameIndex) {
                                            0 -> {
                                                val intent = Intent(
                                                    content,
                                                    MoodTrackerScreen::class.java
                                                )
                                                startActivity(intent)
                                            }

                                            1 -> {
                                                val intent = Intent(
                                                    content,
                                                    AddMedicationScreen::class.java
                                                )
                                                startActivity(intent)
                                            }

                                            2 -> {
                                                val intent = Intent(
                                                    content,
                                                    SleepTrackerScreen::class.java
                                                )
                                                startActivity(intent)
                                            }
                                            3 -> {
                                                val intent = Intent(
                                                    content,
                                                    AddTherapistScreen::class.java
                                                )
                                                startActivity(intent)
                                            }
                                            6 -> {
                                                val intent = Intent(
                                                    content,
                                                    AchievementsScreen::class.java
                                                )
                                                startActivity(intent)
                                            }
                                            8 -> {
                                                val intent = Intent(
                                                    content,
                                                    SettingsScreen::class.java
                                                )
                                                startActivity(intent)
                                            }

                                        }
                                        /*
                                        when (nameIndex) {
                                            0 -> {
                                                val intent = Intent(
                                                    content,
                                                    Estado::class.java
                                                )
                                                startActivity(intent)
                                            }

                                            1 -> {
                                                val intent = Intent(
                                                    content,
                                                    Medicacion::class.java
                                                )
                                                startActivity(intent)
                                            }
                                        }
                                        */
                                    }
                            )
                            Text(
                                text = imageName,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            robotin()


        }
    }

    @Composable
    private fun robotin() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp) // Agregar padding después de aplicar la forma
                .background(
                    Color.LightGray,
                    RoundedCornerShape(48.dp)
                )
        ) {
            Text(
                "Robotin",
                modifier = Modifier.padding(top = 6.dp, start = 25.dp),
                color = secondaryColor, fontSize = 20.sp
            )
            Text(
                "  ¡Hola! ¿Cómo\n amaneciste hoy?",
                modifier = Modifier.padding(top = 22.dp, start = 20.dp), fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.weight(1f)) // Espacio flexible para empujar la imagen al final
            Image(
                painterResource(id = R.drawable.robotin),
                contentDescription = "Fondo",
                modifier = Modifier
                    .size(85.dp)
                    .padding(end = 1.dp)
            )
        }
    }

}