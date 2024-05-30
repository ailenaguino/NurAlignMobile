package com.losrobotines.nuralign.feature_sleep.data

import android.util.Log
import com.losrobotines.nuralign.feature_sleep.data.dto.SleepTrackerDto
import com.losrobotines.nuralign.feature_sleep.data.network.SleepApiService
import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import javax.inject.Inject


class SleepRepositoryImpl @Inject constructor(private val apiService: SleepApiService) :
    SleepRepository {

    override suspend fun saveSleepData(sleepInfo: SleepInfo) {
        try {
            val dto = mapDomainToData(sleepInfo)
            apiService.insertSleepTrackerInfoIntoDatabase(dto)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getSleepData(patientId: Int): SleepInfo? {
        val dto = apiService.getSleepInfo(patientId)
        Log.d("MoodTrackerRepository", "DtO Obtenido: $dto")
        return mapDataToDomain(dto)
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


    private fun mapDomainToData(sleepInfo: SleepInfo): SleepTrackerDto {
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


