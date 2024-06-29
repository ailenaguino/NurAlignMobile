package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data

import android.util.Log
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.dto.MoodTrackerDto
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.network.MoodTrackerApiService
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerProvider
import javax.inject.Inject

class MoodTrackerProviderImpl @Inject constructor(private val apiService: MoodTrackerApiService) :
    MoodTrackerProvider {
    override suspend fun saveMoodTrackerInfo(moodTrackerInfo: MoodTrackerInfo):Boolean {
        return try {
            val dto = mapDomainToData(moodTrackerInfo)
            val result = apiService.insertMoodTrackerInfo(dto)
            result.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getMoodTrackerInfo(patientId: Int, effectiveDate: String): MoodTrackerInfo? {

        val dto = apiService.getMoodTrackerInfo(patientId, effectiveDate)
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

    override suspend fun updateMoodTrackerInfo(moodTrackerInfo: MoodTrackerInfo): Boolean {
        return try {
            val dto = mapDomainToData(moodTrackerInfo)
            val result =
                apiService.updateMoodTrackerInfo(dto.patientId, dto.effectiveDate, dto)
            result!!.isSuccessful
        } catch (e: Exception) {
            false
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