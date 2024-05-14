package com.losrobotines.nuralign.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.losrobotines.nuralign.bottom_bar.Destinations.Home.BottomBarInternet
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
import com.losrobotines.nuralign.home.HomeScreenComponent
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private val signUpViewModel by viewModels<SignUpViewModel>()

    @SuppressLint("RememberReturnType")
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
                    val navController: NavHostController = rememberNavController()
                    val bottomBarHeight = 56.dp
                    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }

                    var buttonsVisible = remember { mutableStateOf(true) }

                    Scaffold(
                        bottomBar = {
                            BottomBarInternet(
                                navController = navController,
                                state = buttonsVisible,
                                modifier = Modifier
                            )
                        }) { paddingValues ->
                        Box(
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            // Use NavigationGraph instead of directly composing HomeScreenComponent
                            NavigationGraphAplication(navController, loginViewModel,signUpViewModel)
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraphAplication(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel
) {
    NavHost(navController, startDestination = Routes.HomeScreen.route) {
        composable(Routes.HomeScreen.route) {
            HomeScreenComponent(navController)
        }
        composable(Routes.SignUpScreen.route) {
            SignUpScreenComponent(navController, signUpViewModel)
        }
        composable(Routes.LoginScreen.route) {
            LoginScreenComponent(navController, loginViewModel)
        }
        composable(Routes.MoodTrackerScreen.route) {
            MoodTrackerScreen(navController)
        }
        composable(Routes.AddMedicationScreen.route) {
            NewMedScreen(navController)
        }
        composable(Routes.SleepTrackerScreen.route) {
            SleepScreen(navController)
        }
        composable(Routes.TherapyTrackerScreen.route) {
            TherapyScreen(navController)
        }
        composable(Routes.AddTherapyScreen.route) {
            AddATherapistScreen(navController)
        }
        composable(Routes.AchievementsScreen.route) {
            AchievementsScreen(navController)
        }
        composable(Routes.SettingsScreen.route) {
            SettingsScreen(navController, loginViewModel)
        }
    }
}
