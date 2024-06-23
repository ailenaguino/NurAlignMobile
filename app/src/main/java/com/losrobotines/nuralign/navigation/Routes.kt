package com.losrobotines.nuralign.navigation

sealed class Routes(val route: String) {
    data object LoginScreen : Routes("LoginScreen")
    data object SignUpScreen : Routes("SignUpScreen")
    data object HomeScreen : Routes("HomeScreen")
    data object MoodTrackerScreen : Routes("MoodTrackerScreen")
    data object MedicationTrackerScreen : Routes("MedicationTrackerScreen")
    data object SleepTrackerScreen : Routes("SleepTrackerScreen")
    data object TherapyTrackerScreen : Routes("TherapyTrackerScreen")
    data object AchievementsScreen : Routes("AchievementsScreen")
    data object SettingsScreen : Routes("SettingsScreen")
    data object PersonalInformationScreen : Routes("PersonalInformation")
    data object RoutineScreen : Routes("RoutineScreen")
    data object LoadingScreen : Routes("LoadingScreen")

    data object TestGraficos :Routes("TestGraficos")
    data object TherapySessionScreen : Routes("TherapySessionScreen")


}