package com.losrobotines.nuralign.feature_achievements.domain.usecases

import com.losrobotines.nuralign.feature_achievements.domain.AchievementRepository
import com.losrobotines.nuralign.feature_achievements.domain.models.Achievement
import javax.inject.Inject

class GetUserAchievementsUseCase @Inject constructor(private val repository: AchievementRepository){
    suspend operator fun invoke(): List<Achievement> {
        return repository.getUserAchievements()
    }
}