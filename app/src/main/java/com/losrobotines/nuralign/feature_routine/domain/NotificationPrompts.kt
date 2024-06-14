package com.losrobotines.nuralign.feature_routine.domain

import com.losrobotines.nuralign.feature_routine.presentation.RoutineViewModel
import javax.inject.Inject

class NotificationPrompts {

    companion object {
        const val MOTIVATIONAL_MESSAGE: String = "Genera un mensaje para la notificación que contiene un mensaje motivacional para empezar el día y recomendarle que complete el SleepTracker para saber cómo durmió. Máximo 40 palabras."

        fun getActivityMessage(activity: String): String {
            return "Genera un mensaje de aliento para la actividad que el usuario está por hacer, la actividad es $activity, de máximo 40 palabras."
        }
    }
}