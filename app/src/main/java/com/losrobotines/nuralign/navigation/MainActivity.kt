package com.losrobotines.nuralign.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsScreen
import com.losrobotines.nuralign.feature_login.presentation.screens.login.LoginScreenComponent
import com.losrobotines.nuralign.feature_login.presentation.screens.login.LoginViewModel
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.SignUpScreenComponent
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.SignUpViewModel
import com.losrobotines.nuralign.feature_medication.presentation.screens.NewMedScreen
import com.losrobotines.nuralign.feature_mood_tracker_presentation_screens.MoodTrackerScreen
import com.losrobotines.nuralign.feature_settings.presentation.screens.SettingsScreen
import com.losrobotines.nuralign.feature_sleep.presentation.screens.SleepScreen
import com.losrobotines.nuralign.feature_therapy.presentation.screens.AddATherapistScreen
import com.losrobotines.nuralign.feature_therapy.presentation.screens.TherapyScreen
import com.losrobotines.nuralign.home.BottomNavigationBar
import com.losrobotines.nuralign.home.HomeScreenComponent
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private val sigUpViewModel by viewModels<SignUpViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurAlignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.LoginScreen.route
                    ) {

                        composable(Routes.LoginScreen.route) {
                            LoginScreenComponent(
                                navigationController,
                                loginViewModel
                            )
                        }
                        composable(Routes.SignUpScreen.route) {
                            SignUpScreenComponent(
                                navigationController,
                                sigUpViewModel
                            )
                        }
                        composable(Routes.HomeScreen.route) {
                            HomeScreenComponent(
                                navigationController
                            )
                        }
                        composable(Routes.MoodTrackerScreen.route) {
                            MoodTrackerScreen(
                                navigationController
                            )
                        }
                        composable(Routes.AddMedicationScreen.route) {
                            NewMedScreen(
                                navigationController
                            )
                        }
                        composable(Routes.SleepTrackerScreen.route) {
                            SleepScreen(
                                navigationController
                            )
                        }
                        composable(Routes.TherapyTrackerScreen.route) {
                            TherapyScreen(
                                navigationController
                            )
                        }
                        composable(Routes.AddTherapyScreen.route) {
                            AddATherapistScreen(
                                navigationController
                            )
                        }
                        composable(Routes.AchievementsScreen.route) {
                            AchievementsScreen(
                                navigationController
                            )
                        }
                        composable(Routes.SettingsScreen.route) {
                            SettingsScreen(
                                navigationController,
                                loginViewModel
                            )
                        }

                    }

                }
            }
        }
    }
}

