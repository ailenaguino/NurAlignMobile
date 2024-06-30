package com.losrobotines.nuralign.feature_sleep.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.losrobotines.nuralign.feature_sleep.data.dto.SleepTrackerDto
import com.losrobotines.nuralign.feature_sleep.data.network.SleepApiService
import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class SleepTrackerProviderImpl @Inject constructor(private val apiService: SleepApiService) :
    SleepTrackerProvider {

    override suspend fun saveSleepData(sleepInfo: SleepInfo): Boolean {
        return try {
            val dto = mapDomainToData(sleepInfo)
            val result = apiService.insertSleepTrackerInfo(dto)
            result.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getSleepData(patientId: Int, effectiveDate: String): SleepInfo? {
        val dto = apiService.getSleepInfo(patientId, effectiveDate)
        return try {
            if (dto.isSuccessful) {
                mapDataToDomain(dto.body())
            } else {
                //Lo dejo así hasta que esté hecho que cuando no haya tracker en el día
                //la API devuelva 204. Por el momento devuelve 500.
                //Una vez que devuelva 204, el null pasa a devolver una Exception
                null
            }
        } catch (e: Exception) {
            throw Exception("Failed to get data")
        }
    }

    override suspend fun updateSleepData(sleepTrackerInfo: SleepInfo): Boolean {
        return try {
            val dto = mapDomainToData(sleepTrackerInfo)
            val result =
                apiService.updateSleepTrackerInfo(dto.patientId, dto.effectiveDate, dto)
            result!!.isSuccessful
        } catch (e: Exception) {
            false
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


