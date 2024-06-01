package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data

import android.util.Log
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.dto.MoodTrackerDto
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.network.MoodTrackerApiService
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerProvider
import retrofit2.HttpException
import javax.inject.Inject

class MoodTrackerProviderImpl @Inject constructor(private val apiService: MoodTrackerApiService) :
    MoodTrackerProvider {
    override suspend fun saveMoodTrackerInfo(moodTrackerInfo: MoodTrackerInfo) {
        try {
            val dto = mapDomainToData(moodTrackerInfo)
            Log.d("MoodTrackerRepository", "DtO Generado: $dto")
            apiService.insertMoodTrackerInfoIntoDatabase(dto)
            Log.d("MoodTrackerRepository", "Successfully saved MoodTrackerInfo")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("MoodTrackerRepository", "Error saving MoodTrackerInfo", e)
        }
    }

    override suspend fun getMoodTrackerInfo(patientId: Int): MoodTrackerInfo? {

        val dto = apiService.getMoodTrackerInfo(patientId)
        Log.d("MoodTrackerRepository", "DtO Obtenido: $dto")
        return mapDataToDomain(dto)
    }

    override suspend fun getTodaysTracker(patientId: Int, date: String): MoodTrackerInfo? {
        try {
            //val response = apiService.getTodaysTracker(patientId,date)
            val response = null
            return mapDataToDomain(response)
        } catch (e: HttpException) {
            return null
        }
    }


    private fun mapDataToDomain(dto: MoodTrackerDto?): MoodTrackerInfo? {
        return dto?.let {
            it.highestNotes?.let { it1 ->
                it.lowestNotes?.let { it2 ->
                    it.irritableNotes?.let { it3 ->
                        it.anxiousNotes?.let { it4 ->
                            MoodTrackerInfo(
                                patientId = it.patientId,
                                effectiveDate = it.effectiveDate,
                                highestValue = it.highestValue,
                                lowestValue = it.lowestValue,
                                highestNote = it1,
                                lowestNote = it2,
                                irritableValue = it.irritableValue,
                                irritableNote = it3,
                                anxiousValue = it.anxiousValue,
                                anxiousNote = it4
                            )
                        }
                    }
                }
            }
        }
    }


    private fun mapDomainToData(moodTrackerInfo: MoodTrackerInfo): MoodTrackerDto {
        return MoodTrackerDto(
            patientId = moodTrackerInfo.patientId,
            effectiveDate = moodTrackerInfo.effectiveDate,
            highestValue = moodTrackerInfo.highestValue,
            lowestValue = moodTrackerInfo.lowestValue,
            highestNotes = moodTrackerInfo.highestNote,
            lowestNotes = moodTrackerInfo.lowestNote,
            irritableValue = moodTrackerInfo.irritableValue,
            irritableNotes = moodTrackerInfo.irritableNote,
            anxiousValue = moodTrackerInfo.anxiousValue,
            anxiousNotes = moodTrackerInfo.anxiousNote
        )
    }
}