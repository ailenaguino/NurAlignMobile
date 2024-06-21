package com.losrobotines.nuralign.feature_achievements.data

import com.losrobotines.nuralign.feature_achievements.data.database.AchievementDao
import com.losrobotines.nuralign.feature_achievements.data.database.CounterDao
import com.losrobotines.nuralign.feature_achievements.data.database.entities.AchievementEntity
import com.losrobotines.nuralign.feature_achievements.data.database.entities.CounterEntity
import com.losrobotines.nuralign.feature_achievements.domain.AchievementRepository
import com.losrobotines.nuralign.feature_achievements.domain.models.Achievement
import com.losrobotines.nuralign.feature_achievements.domain.models.Counter
import com.losrobotines.nuralign.feature_home.presentation.utils.HomeItemData
import javax.inject.Inject

class AchievementRepositoryImpl @Inject constructor(
    private val achievementDao: AchievementDao,
    private val counterDao: CounterDao,
) : AchievementRepository {
    override suspend fun getUserAchievements(): List<Achievement> {
        val list = achievementDao.getAllAchievements()
        return if (!list.isNullOrEmpty()){
            mapDataToDomain(list)
        }else{
            emptyList()
        }
    }

    override suspend fun addAchievement(achievement: Achievement) {
        achievementDao.insertAchievement(AchievementEntity(achievement = achievement.name))
    }

    override suspend fun getTrackerCounter(tracker: String): Counter? {
        val counter = counterDao.getCounterFromTracker(tracker)
        return if(counter!=null) mapCounterDataToDomain(counter) else counter
    }

    override suspend fun startCounter(counter: Counter) {
        counterDao.startCounter(mapCounterDomainToData(counter))
    }

    override suspend fun addOneToCounter(tracker: String) {
        counterDao.updateCounter(tracker)
    }

    override suspend fun restartCounters() {
        counterDao.restartCounters()
    }

    private fun mapDataToDomain(achievementList: List<AchievementEntity>): List<Achievement>{
        val list = mutableListOf<Achievement>()
        for(achievement in achievementList){
            list.add(Achievement(achievement.achievement))
        }
        return list
    }

    private fun mapCounterDataToDomain(counter: CounterEntity): Counter{
        return Counter(counter.tracker, counter.counter)
    }

    private fun mapCounterDomainToData(counter: Counter): CounterEntity{
        return CounterEntity(counter = counter.counter, tracker = counter.tracker)
    }
}