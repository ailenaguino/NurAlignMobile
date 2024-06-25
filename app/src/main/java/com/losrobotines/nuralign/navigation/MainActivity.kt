package com.losrobotines.nuralign.navigation

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsScreenComponent
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsViewModel
import com.losrobotines.nuralign.feature_home.presentation.screens.HomeScreenComponent
import com.losrobotines.nuralign.feature_login.presentation.screens.login.LoginScreenComponent
import com.losrobotines.nuralign.feature_login.presentation.screens.login.LoginViewModel
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.SignUpScreenComponent
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.SignUpViewModel
import com.losrobotines.nuralign.feature_login.presentation.utils.LoginState
import com.losrobotines.nuralign.feature_medication.presentation.screens.medication.MedicationViewModel
import com.losrobotines.nuralign.feature_medication.presentation.screens.tracker.MedicationTrackerScreenComponent
import com.losrobotines.nuralign.feature_medication.presentation.screens.tracker.MedicationTrackerViewModel
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation.MoodTrackerScreenComponent
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation.MoodTrackerViewModel
import com.losrobotines.nuralign.feature_resumen_semanal.MoodBarChartExample
import com.losrobotines.nuralign.feature_routine.domain.notification.NotificationHelper
import com.losrobotines.nuralign.feature_routine.domain.notification.PermissionManager
import com.losrobotines.nuralign.feature_routine.presentation.RoutineScreenComponent
import com.losrobotines.nuralign.feature_routine.presentation.RoutineViewModel
import com.losrobotines.nuralign.feature_settings.presentation.screens.personal_information.PersonalInformationScreenComponent
import com.losrobotines.nuralign.feature_settings.presentation.screens.settings.SettingsScreenComponent
import com.losrobotines.nuralign.feature_sleep.presentation.screens.SleepTrackerScreenComponent
import com.losrobotines.nuralign.feature_sleep.presentation.screens.SleepViewModel
import com.losrobotines.nuralign.feature_therapy.presentation.screens.session_history.TherapySessionHistoryScreenComponent
import com.losrobotines.nuralign.feature_therapy.presentation.screens.session_history.TherapySessionHistoryViewModel
import com.losrobotines.nuralign.feature_therapy.presentation.screens.therapists.TherapistScreenComponent
import com.losrobotines.nuralign.feature_therapy.presentation.screens.therapists.TherapistViewModel
import com.losrobotines.nuralign.feature_therapy.presentation.screens.therapy_session.TherapySessionScreenComponent
import com.losrobotines.nuralign.feature_therapy.presentation.screens.therapy_session.TherapySessionViewModel
import com.losrobotines.nuralign.ui.bottom_bar.Destinations.Home.BottomBarNavigation
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var permissionManager: PermissionManager

    @SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter", "InlinedApi", "StateFlowValueCalledInComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionManager = PermissionManager(this)
        NotificationHelper.createNotificationChannel(this)

        setContent {
            NurAlignTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    setLocaleConfig()

                    val navController = rememberNavController()

                    val startDestination = remember { mutableStateOf(Routes.LoadingScreen.route) }

                    LaunchedEffect(Unit) {
                        delay(500)
                        val isAuthenticated = loginViewModel.loginFlow.value is LoginState.Success
                        startDestination.value = if (isAuthenticated) {
                            Routes.HomeScreen.route
                        } else {
                            Routes.LoginScreen.route
                        }
                    }

                    Scaffold(
                        bottomBar = {
                            if (startDestination.value !in listOf(Routes.LoadingScreen.route, Routes.LoginScreen.route, Routes.SignUpScreen.route)) {
                                BottomBarNavigation(
                                    navController = navController,
                                    modifier = Modifier
                                )
                            }
                        }
                    ) {
                        Box(modifier = Modifier.padding(it)) {
                            NavHost(navController, startDestination = startDestination.value) {
                                composable(Routes.SignUpScreen.route) {
                                    val signUpViewModel by viewModels<SignUpViewModel>()
                                    SignUpScreenComponent(navController, signUpViewModel)
                                }
                                composable(Routes.LoginScreen.route) {
                                    LoginScreenComponent(navController, loginViewModel)
                                }
                                composable(Routes.HomeScreen.route) {
                                    HomeScreenComponent(navController)
                                }
                                composable(Routes.MoodTrackerScreen.route) {
                                    val moodTrackerViewModel by viewModels<MoodTrackerViewModel>()
                                    MoodTrackerScreenComponent(navController, moodTrackerViewModel)
                                }
                                composable(Routes.MedicationTrackerScreen.route) {
                                    val medicationViewModel by viewModels<MedicationViewModel>()
                                    val medicationTrackerViewModel by viewModels<MedicationTrackerViewModel>()
                                    MedicationTrackerScreenComponent(navController, medicationViewModel, medicationTrackerViewModel)
                                }
                                composable(Routes.SleepTrackerScreen.route) {
                                    val sleepViewModel by viewModels<SleepViewModel>()
                                    SleepTrackerScreenComponent(navController, sleepViewModel)
                                }
                                composable(Routes.TherapistScreen.route) {
                                    val therapistViewModel by viewModels<TherapistViewModel>()
                                    TherapistScreenComponent(navController, therapistViewModel)
                                }
                                composable(Routes.AchievementsScreen.route) {
                                    val achievementsViewModel by viewModels<AchievementsViewModel>()
                                    AchievementsScreenComponent(navController, achievementsViewModel)
                                }
                                composable(Routes.SettingsScreen.route) {
                                    SettingsScreenComponent(navController, loginViewModel)
                                }
                                composable(Routes.PersonalInformationScreen.route) {
                                    PersonalInformationScreenComponent(navController)
                                }
                                composable(Routes.RoutineScreen.route) {
                                    val routineViewModel by viewModels<RoutineViewModel>()
                                    RoutineScreenComponent(navController, routineViewModel)
                                }
                                composable(Routes.LoadingScreen.route) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                                //******************************************************************************
                                composable(Routes.TestGraficos.route) {
                                    MoodBarChartExample()
                                }
                                //*****************************************************************************
                                composable(Routes.TherapySessionScreen.route) {
                                    val therapistViewModel by viewModels<TherapySessionViewModel>()
                                    TherapySessionScreenComponent(navController, therapistViewModel)
                                }
                                composable(Routes.TherapySessionHistoryScreen.route){
                                    val therapySessionHistoryViewModel by viewModels<TherapySessionHistoryViewModel>()
                                    val therapySessionViewModel by viewModels<TherapySessionViewModel>()
                                    TherapySessionHistoryScreenComponent(navController, therapySessionHistoryViewModel, therapySessionViewModel)
                                }
                            }

                            LaunchedEffect(navController) {
                                val isAuthenticated = loginViewModel.loginFlow.value is LoginState.Success
                                if (isAuthenticated) {
                                    permissionManager.requestPermissions()
                                    val currentIntent = intent
                                    val destination = currentIntent?.getStringExtra("destination")
                                    if (destination != null) {
                                        when (destination) {
                                            "SleepTrackerScreen" -> {
                                                navController.navigate(Routes.SleepTrackerScreen.route)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setLocaleConfig(){
        val locale = Locale("es")
        Locale.setDefault(locale)

        val config: Configuration = baseContext.resources.configuration
        config.setLocale(locale)

        createConfigurationContext(config)
    }
}
