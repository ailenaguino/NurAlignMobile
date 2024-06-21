package com.losrobotines.nuralign.feature_achievements.domain.usecases

import com.losrobotines.nuralign.feature_achievements.domain.AchievementRepository
import com.losrobotines.nuralign.feature_achievements.domain.models.Achievement
import javax.inject.Inject

class AddAchievementUseCase @Inject constructor(private val repository: AchievementRepository) {
    suspend operator fun invoke(achievement: Achievement){
        repository.addAchievement(achievement)
    }
}