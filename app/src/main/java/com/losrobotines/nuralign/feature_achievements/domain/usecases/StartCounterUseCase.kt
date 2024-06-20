package com.losrobotines.nuralign.feature_achievements.domain.usecases

import com.losrobotines.nuralign.feature_achievements.domain.AchievementRepository
import com.losrobotines.nuralign.feature_achievements.domain.models.Counter
import javax.inject.Inject

class StartCounterUseCase @Inject constructor(private val repository: AchievementRepository) {

    suspend operator fun invoke(tracker:String){
        repository.startCounter(Counter(tracker, 1))
    }
}