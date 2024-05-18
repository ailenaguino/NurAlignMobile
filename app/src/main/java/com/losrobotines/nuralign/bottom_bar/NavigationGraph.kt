package com.losrobotines.nuralign.bottom_bar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsScreenComponent
import com.losrobotines.nuralign.feature_login.presentation.screens.login.LoginViewModel
import com.losrobotines.nuralign.feature_settings.presentation.screens.settings.SettingsScreenComponent
import com.losrobotines.nuralign.home.HomeScreenComponent

@Composable
fun NavigationGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {

    NavHost(navController, startDestination = Destinations.Home.route) {
        composable(Destinations.Asistencia.route) {
            AchievementsScreenComponent(
                navController
            )
        }
        composable(Destinations.Home.route) {
          HomeScreenComponent(navController)
        }
        composable(Destinations.Configuracion.route) {
            SettingsScreenComponent(
                navController,
                loginViewModel
            )
        }
    }
}