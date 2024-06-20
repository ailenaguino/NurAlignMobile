package com.losrobotines.nuralign.feature_achievements.domain.usecases

import com.losrobotines.nuralign.feature_achievements.domain.models.Counter
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsViewModel
import javax.inject.Inject

class SendNotificationIfNeededUseCase @Inject constructor() {

    operator fun invoke(counter: Counter) {
        val message = sendMessage(counter)
    }

    private fun sendMessage(counter: Counter): String{
        return when(counter.tracker){
            AchievementsViewModel.TrackerConstants.MOOD_TRACKER ->
            {when(counter.counter){
                1-> "¡Felicitaciones! Conseguiste el logro Ánimo de Bronce, gracias por elegir NurAlign."
                7-> "¡Felicitaciones! Conseguiste el logro Ánimo de Plata, vamos por buen camino."
                30-> "¡Felicitaciones! Conseguiste el logro Ánimo de Oro, es increíble, mirá de todo lo que sos capaz."
                else-> ""
            }}
            AchievementsViewModel.TrackerConstants.SLEEP_TRACKER ->
            {when(counter.counter){
                1-> "¡Felicitaciones! Conseguiste el logro Sueño de Bronce, gracias por elegir NurAlign."
                7-> "¡Felicitaciones! Conseguiste el logro Sueño de Plata, vamos por buen camino."
                30-> "¡Felicitaciones! Conseguiste el logro Sueño de Oro, es increíble, mirá de todo lo que sos capaz."
                else-> ""
            }}
            AchievementsViewModel.TrackerConstants.THERAPY_TRACKER ->
            {when(counter.counter){
                1-> "¡Felicitaciones! Conseguiste el logro Terapia de Bronce, gracias por elegir NurAlign."
                7-> "¡Felicitaciones! Conseguiste el logro Terapia de Plata, vamos por buen camino."
                30-> "¡Felicitaciones! Conseguiste el logro Terapia de Oro, es increíble, mirá de todo lo que sos capaz."
                else-> ""
            }}
            AchievementsViewModel.TrackerConstants.MEDICATION_TRACKER ->
            {when(counter.counter){
                1-> "¡Felicitaciones! Conseguiste el logro Medicina de Bronce, gracias por elegir NurAlign."
                7-> "¡Felicitaciones! Conseguiste el logro Medicina de Plata, vamos por buen camino."
                30-> "¡Felicitaciones! Conseguiste el logro Medicina de Oro, es increíble, mirá de todo lo que sos capaz."
                else-> ""
            }}
            else -> ""
        }
    }
}