package com.losrobotines.nuralign.feature_sleep.data

import android.util.Log
import com.losrobotines.nuralign.feature_sleep.data.models.SleepTrackerDto
import com.losrobotines.nuralign.feature_sleep.data.network.SleepApiService
import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import javax.inject.Inject


class SleepRepositoryImpl @Inject constructor(private val apiService: SleepApiService) : SleepRepository {

    override suspend fun saveSleepData(sleepInfo: SleepInfo) {
        try {
            val c = mapDomainToData(sleepInfo)
            //val body: RequestBody = create("application/json".toMediaTypeOrNull(), c.toString())
            //val requestBody = c.toString().toRequestBody("application/json".toMediaTypeOrNull())
            Log.i("post retrofit", "$c")
            apiService.insertSleepTrackerInfoIntoDatabase(c)
        } catch(e:IllegalArgumentException){
            e.printStackTrace()
        }
    }

    private fun mapDomainToData(sleepInfo: SleepInfo):SleepTrackerDto{
        return SleepTrackerDto(
            patientId = sleepInfo.patientId,
            sleepHours = sleepInfo.sleepHours,
            bedTime = sleepInfo.bedTime,
            negativeThoughtsFlag = sleepInfo.negativeThoughtsFlag,
            anxiousFlag = sleepInfo.anxiousFlag,
            sleepStraightFlag = sleepInfo.sleepStraightFlag,
            sleepNotes = sleepInfo.sleepNotes,
            effectiveDate = sleepInfo.effectiveDate)
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