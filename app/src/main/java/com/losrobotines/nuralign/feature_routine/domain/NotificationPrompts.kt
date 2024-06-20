package com.losrobotines.nuralign.feature_routine.domain

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.USER_NAME
import com.losrobotines.nuralign.feature_routine.presentation.RoutineViewModel
import com.losrobotines.nuralign.ui.preferences.PreferencesManager
import javax.inject.Inject

class NotificationPrompts {

    companion object {

        fun getMotivationalMessage(name: String): String {
            return "Genera un mensaje para la notificación que contiene un mensaje motivacional para empezar el día y recomendarle que complete el SleepTracker para saber cómo durmió. El nombre del usuario es $name. Máximo 40 palabras."
        }

        fun getActivityMessage(activity: String, name: String): String {
            return "Genera un mensaje de aliento para la actividad que el usuario está por hacer. La actividad es $activity y el nombre del usuario es $name. Máximo 40 palabras."
        }
    }
}