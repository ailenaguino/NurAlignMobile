package com.losrobotines.nuralign.feature_sleep.data

import android.util.Log
import com.losrobotines.nuralign.feature_sleep.data.models.SleepTrackerDto
import com.losrobotines.nuralign.feature_sleep.data.network.SleepApiService
import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject

class SleepRepositoryImpl @Inject constructor(private val apiService: SleepApiService) : SleepRepository {

    override suspend fun saveSleepData(sleepInfo: SleepInfo) {
        val c = apiService.insertSleepTrackerInfoIntoDatabase(mapDomainToData(sleepInfo))
        Log.i("call retrofit", "$c")
    }

    private fun mapDomainToData(sleepInfo: SleepInfo):SleepTrackerDto{
        return SleepTrackerDto(
            patientId = sleepInfo.patientId,
            effectiveDate = sleepInfo.effectiveDate,
            sleepHours = sleepInfo.sleepHours,
            bedTime = sleepInfo.bedTime,
            negativeThoughtsFlag = sleepInfo.negativeThoughtsFlag,
            anxiousFlag = sleepInfo.anxiousFlag,
            sleepStraightFlag = sleepInfo.sleepStraightFlag,
            sleepNotes = sleepInfo.sleepNotes)
    }

}

/*
CoroutineScope(Dispatchers.IO).launch {
    database = Database.connect(
        "jdbc:mariadb://77.37.69.38:3306/nuralign",
        user = "root",
        password = "/rt(Chw[-A(K@y8("
    )
}*/