package com.losrobotines.nuralign.feature_sleep.data

import android.util.Log
import com.losrobotines.nuralign.feature_sleep.data.dto.SleepTrackerDto
import com.losrobotines.nuralign.feature_sleep.data.network.SleepApiService
import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject


class SleepTrackerProviderImpl @Inject constructor(private val apiService: SleepApiService) :
    SleepTrackerProvider {

    override suspend fun saveSleepData(sleepInfo: SleepInfo): Boolean {
        try {
            val dto = mapDomainToData(sleepInfo)
            val result = apiService.insertSleepTrackerInfoIntoDatabase(dto)
            return result.isSuccessful
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun getSleepData(patientId: Int): SleepInfo? {
        val dto = apiService.getSleepInfo(patientId)
        return mapDataToDomain(dto)
    }

    override suspend fun getTodaysTracker(patientId: Int, date: String): SleepInfo? {
        try {
            val response = apiService.getTodaysTracker(patientId,date)
            return mapDataToDomain(response?.last())
        } catch (e: HttpException) {
            return null
        }
    }


    private fun mapDataToDomain(dto: SleepTrackerDto?): SleepInfo? {
        return dto?.let {
            SleepInfo(
                patientId = it.patientId,
                effectiveDate = it.effectiveDate,
                sleepHours = it.sleepHours,
                bedTime = it.bedTime,
                negativeThoughtsFlag = it.negativeThoughtsFlag,
                anxiousFlag = it.anxiousFlag,
                sleepStraightFlag = it.sleepStraightFlag,
                sleepNotes = it.sleepNotes,
            )
        }
    }


    fun mapDomainToData(sleepInfo: SleepInfo): SleepTrackerDto {
        return SleepTrackerDto(
            patientId = sleepInfo.patientId,
            sleepHours = sleepInfo.sleepHours,
            bedTime = sleepInfo.bedTime,
            negativeThoughtsFlag = sleepInfo.negativeThoughtsFlag,
            anxiousFlag = sleepInfo.anxiousFlag,
            sleepStraightFlag = sleepInfo.sleepStraightFlag,
            sleepNotes = sleepInfo.sleepNotes,
            effectiveDate = sleepInfo.effectiveDate
        )
    }

}


