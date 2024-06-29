package com.losrobotines.nuralign.feature_weekly_summary.data

import android.util.Log
import com.losrobotines.nuralign.feature_medication.data.dto.MedicationDto
import com.losrobotines.nuralign.feature_medication.data.dto.MedicationTrackerDto
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.data.dto.MoodTrackerDto
import com.losrobotines.nuralign.feature_mood_tracker.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import com.losrobotines.nuralign.feature_sleep.data.dto.SleepTrackerDto
import com.losrobotines.nuralign.feature_sleep.domain.models.SleepInfo
import retrofit2.HttpException
import javax.inject.Inject

class WeeklySummaryProviderImpl @Inject constructor(private val apiService: WeeklySummaryApiService) :
    WeeklySummaryProvider {


    override suspend fun getSleepTracker(patientId: Short, date: String): SleepInfo? {
        return try {
            val response = apiService.getSleepTrackerByDate(patientId, date)
            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.let {
                    mapSleepTrackerDataToDomain(it)
                }
            } else {
                null
            }
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getMedicationTracker(
        patientId: Short,
        date: String
    ): MedicationTrackerInfo? {
        return try {
            val response = apiService.getMedicationTrackerInfo(patientId, date)
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d("MedicationTrackerInfo", responseBody.toString())
                responseBody?.let {
                    mapMedicationTrackerDataToDomain(it)
                }
            } else {
                null
            }
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getMedicationListInfo(
        patientId: Short,
    ): List<MedicationInfo?> {
        val dto = apiService.getMedicationList(patientId)
        Log.d("MedicatioList", "DtO Obtenido: $dto")
        return mapMedicationDataToDomain(dto)
    }



    override suspend fun getMoodTracker(patientId: Short, date: String): MoodTrackerInfo? {
        return try {
            val response = apiService.getMoodTrackerByDate(patientId, date)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    mapMoodTrackerDataToDomain(responseBody)
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }
    }


    private fun mapMoodTrackerDataToDomain(dto: MoodTrackerDto?): MoodTrackerInfo? {
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

    private fun mapSleepTrackerDataToDomain(dto: SleepTrackerDto?): SleepInfo? {
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

    private fun mapDataToDomain(dto: MedicationTrackerDto?): MedicationTrackerInfo? {
        return dto?.let {
            MedicationTrackerInfo(
                patientMedicationId = it.patientMedicationId,
                effectiveDate = it.effectiveDate,
                takenFlag = it.takenFlag
            )
        }
    }

    private fun mapMedicationTrackerDataToDomain(dto: MedicationTrackerDto?): MedicationTrackerInfo? {
        return dto?.let {
            MedicationTrackerInfo(
                patientMedicationId = it.patientMedicationId,
                effectiveDate = it.effectiveDate,
                takenFlag = it.takenFlag
            )
        }
    }

    private fun mapMedicationDataToDomain(dto: List<MedicationDto?>): List<MedicationInfo?> {
        val list = mutableListOf<MedicationInfo?>()
        dto.let {
            for (med in it) {
                if (med != null) {
                    list.add(
                        MedicationInfo(
                            patientMedicationId = med.patientMedicationId,
                            patientId = med.patientId,
                            medicationName = med.name,
                            medicationGrammage = med.grammage,
                            medicationOptionalFlag = med.flag
                        )
                    )
                }
            }
        }
        return list
    }


}