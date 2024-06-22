package com.losrobotines.nuralign.feature_achievements.domain.usecases

import com.losrobotines.nuralign.feature_achievements.domain.AchievementRepository
import javax.inject.Inject

class AddOneToCounterUseCase @Inject constructor(private val repository: AchievementRepository) {

    suspend operator fun invoke(tracker: String){
        repository.addOneToCounter(tracker)
    }
}