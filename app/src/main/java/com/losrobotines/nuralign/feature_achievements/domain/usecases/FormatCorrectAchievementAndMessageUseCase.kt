package com.losrobotines.nuralign.feature_achievements.domain.usecases

import com.losrobotines.nuralign.feature_achievements.domain.models.Achievement
import com.losrobotines.nuralign.feature_achievements.domain.models.Achievement_Names
import com.losrobotines.nuralign.feature_achievements.domain.models.Counter
import com.losrobotines.nuralign.feature_achievements.presentation.screens.AchievementsViewModel
import javax.inject.Inject

class FormatCorrectAchievementAndMessageUseCase @Inject constructor(private val addAchievementUseCase: AddAchievementUseCase) {

    suspend operator fun invoke(counter: Counter): String {
        return when (counter.tracker) {
            AchievementsViewModel.TrackerConstants.MOOD_TRACKER -> {
                when (counter.counter) {
                    1 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.MOOD_BRONCE))
                        "¡Felicitaciones! Conseguiste el logro Ánimo de Bronce, gracias por elegir NurAlign."
                    }
                    7 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.MOOD_PLATA))
                        "¡Felicitaciones! Conseguiste el logro Ánimo de Plata, vamos por buen camino."
                    }
                    30 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.MOOD_ORO))
                        "¡Felicitaciones! Conseguiste el logro Ánimo de Oro, es increíble, mirá de todo lo que sos capaz."
                    }
                    else -> ""
                }
            }

            AchievementsViewModel.TrackerConstants.SLEEP_TRACKER -> {
                when (counter.counter) {
                    1 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.SLEEP_BRONCE))
                        "¡Felicitaciones! Conseguiste el logro Sueño de Bronce, gracias por elegir NurAlign."
                    }
                    7 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.SLEEP_PLATA))
                        "¡Felicitaciones! Conseguiste el logro Sueño de Plata, vamos por buen camino."
                    }
                    30 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.SLEEP_ORO))
                        "¡Felicitaciones! Conseguiste el logro Sueño de Oro, es increíble, mirá de todo lo que sos capaz."
                    }
                    else -> ""
                }
            }

            AchievementsViewModel.TrackerConstants.THERAPY_TRACKER -> {
                when (counter.counter) {
                    1 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.THERAPY_BRONCE))
                        "¡Felicitaciones! Conseguiste el logro Terapia de Bronce, gracias por elegir NurAlign."
                    }
                    7 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.THERAPY_PLATA))
                        "¡Felicitaciones! Conseguiste el logro Terapia de Plata, vamos por buen camino."
                    }
                    30 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.THERAPY_ORO))
                        "¡Felicitaciones! Conseguiste el logro Terapia de Oro, es increíble, mirá de todo lo que sos capaz."
                    }
                    else -> ""
                }
            }

            AchievementsViewModel.TrackerConstants.MEDICATION_TRACKER -> {
                when (counter.counter) {
                    1 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.MEDICATION_BRONCE))
                        "¡Felicitaciones! Conseguiste el logro Medicina de Bronce, gracias por elegir NurAlign."
                    }
                    7 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.MEDICATION_PLATA))
                        "¡Felicitaciones! Conseguiste el logro Medicina de Plata, vamos por buen camino."
                    }
                    30 -> {
                        addAchievementUseCase(Achievement(Achievement_Names.MEDICATION_ORO))
                        "¡Felicitaciones! Conseguiste el logro Medicina de Oro, es increíble, mirá de todo lo que sos capaz."
                    }
                    else -> ""
                }
            }

            else -> ""
        }
    }
}