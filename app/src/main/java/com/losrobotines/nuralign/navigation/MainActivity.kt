package com.losrobotines.nuralign.navigation


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.losrobotines.nuralign.ui.bottom_bar.Destinations.Home.BottomBarNavigation
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsScreenComponent
import com.losrobotines.nuralign.feature_login.presentation.screens.login.LoginScreenComponent
import com.losrobotines.nuralign.feature_login.presentation.screens.login.LoginViewModel
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.SignUpScreenComponent
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.SignUpViewModel
import com.losrobotines.nuralign.feature_login.presentation.utils.LoginState
import com.losrobotines.nuralign.feature_medication.presentation.screens.AddMedicationScreenComponent
import com.losrobotines.nuralign.feature_settings.presentation.screens.personal_information.PersonalInformationScreenComponent
import com.losrobotines.nuralign.feature_settings.presentation.screens.settings.SettingsScreenComponent
import com.losrobotines.nuralign.feature_sleep.presentation.screens.SleepTrackerScreenComponent
import com.losrobotines.nuralign.feature_sleep.presentation.screens.SleepViewModel
import com.losrobotines.nuralign.feature_therapy.presentation.screens.AddTherapistScreenComponent
import com.losrobotines.nuralign.feature_therapy.presentation.screens.TherapyTrackerScreenComponent
import com.losrobotines.nuralign.feature_home.presentation.screens.HomeScreenComponent
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation.MoodTrackerScreenComponent
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.presentation.MoodTrackerViewModel
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

    @SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter", "InlinedApi")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var permissionStatus by remember { mutableStateOf("Permiso no solicitado") }

            val permissionLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { granted ->
                    permissionStatus = if (granted) "Permiso concedido" else "Permiso denegado"
                }

            val exactAlarmLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                // Verificar si el permiso ha sido concedido después de la solicitud
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                permissionStatus = if (alarmManager.canScheduleExactAlarms()) {
                    "Permiso de alarma exacta concedido"
                } else {
                    "Permiso de alarma exacta denegado"
                }
            }

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    if (!alarmManager.canScheduleExactAlarms()) {
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        exactAlarmLauncher.launch(intent)
                    }
                }
            }

            NurAlignTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController: NavHostController = rememberNavController()
                    val loginState by loginViewModel.loginFlow.collectAsState(null)
                    val isAuthenticated = loginState is LoginState.Success

                    val startDestination = when (loginState) {
                        is LoginState.Success -> Routes.HomeScreen.route
                        else -> Routes.LoginScreen.route
                    }

                    Scaffold(
                        bottomBar = {
                            if (isAuthenticated) {
                                BottomBarNavigation(
                                    navController = navController,
                                    modifier = Modifier
                                )
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            NavHost(navController, startDestination = startDestination) {
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
                                composable(Routes.AddMedicationScreen.route) {
                                    AddMedicationScreenComponent(navController)
                                }
                                composable(Routes.SleepTrackerScreen.route) {
                                    val sleepViewModel by viewModels<SleepViewModel>()
                                    SleepTrackerScreenComponent(navController, sleepViewModel)
                                }
                                composable(Routes.TherapyTrackerScreen.route) {
                                    TherapyTrackerScreenComponent(navController)
                                }
                                composable(Routes.AddTherapyScreen.route) {
                                    AddTherapistScreenComponent(navController)
                                }
                                composable(Routes.AchievementsScreen.route) {
                                    AchievementsScreenComponent(navController)
                                }
                                composable(Routes.SettingsScreen.route) {
                                    SettingsScreenComponent(navController, loginViewModel)
                                }
                                composable(Routes.PersonalInformationScreen.route) {
                                    PersonalInformationScreenComponent(navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
