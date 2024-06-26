package com.losrobotines.nuralign.feature_resumen_semanal.data

import android.util.Log
import com.losrobotines.nuralign.feature_medication.data.dto.MedicationTrackerDto
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.dto.MoodTrackerDto
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.models.MoodTrackerInfo
import com.losrobotines.nuralign.feature_resumen_semanal.domain.WeeklySummaryProvider
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
                Log.d(
                    "SleepTrackerRepository",
                    "DTO Obtained from API: ${responseBody?.toString()}"
                )
                responseBody?.let {
                    mapSleepTrackerDataToDomain(it)
                }
            } else {
                Log.d(
                    "SleepTrackerRepository",
                    "Unsuccessful response: ${response.errorBody()?.string()}"
                )
                null
            }
        } catch (e: HttpException) {
            Log.d("Exception", "HttpException: ${e.message()}")
            null
        } catch (e: Exception) {
            Log.d("Exception", "General exception: ${e.message}")
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
                Log.d("MedicationTrackerRepository", "DTO Obtained from API: ${responseBody?.toString()}")
                if (responseBody != null) {
                    mapDataToDomain(responseBody)
                } else {
                    Log.d("MedicationTrackerRepository", "Response body is null")
                    null
                }
            } else {
                Log.d(
                    "MedicationTrackerRepository",
                    "Unsuccessful response: ${response.errorBody()?.string()}"
                )
                null
            }
        } catch (e: HttpException) {
            Log.d("Exception", "HttpException: ${e.message()}")
            null
        } catch (e: Exception) {
            Log.d("Exception", "General exception: ${e.message}")
            null
        }
    }

    override suspend fun getMedicationListInfo(
        patientId: Short,
        medicationId: Short
    ): MedicationInfo? {
        TODO("Not yet implemented")
    }

    /*
        override suspend fun getMedicationListInfo(
            patientId: Short,
            medicationId: Short
        ): MedicationInfo? {
            return try {
                val response = apiService.getMedicationListInfo(patientId, medicationId)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("MedicationRepository", "DTO Obtained from API: ${responseBody?.toString()}")
                    if (responseBody != null) {
                        mapMedicationDataToDomain(responseBody)
                    } else {
                        Log.d("MedicationRepository", "Response body is null")
                        null
                    }
                } else {
                    Log.d(
                        "MedicationRepository",
                        "Unsuccessful response: ${response.errorBody()?.string()}"
                    )
                    null
                }
            } catch (e: HttpException) {
                Log.d("Exception", "HttpException: ${e.message()}")
                null
            } catch (e: Exception) {
                Log.d("Exception", "General exception: ${e.message}")
                null
            }
        }

     */
    override suspend fun getMoodTracker(patientId: Short, date: String): MoodTrackerInfo? {
        return try {
            val response = apiService.getMoodTrackerByDate(patientId, date)
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d("MoodTrackerRepository", "DTO Obtained from API: ${responseBody?.toString()}")
                if (responseBody != null) {
                    mapMoodTrackerDataToDomain(responseBody)
                } else {
                    Log.d("MoodTrackerRepository", "Response body is null")
                    null
                }
            } else {
                Log.d(
                    "MoodTrackerRepository",
                    "Unsuccessful response: ${response.errorBody()?.string()}"
                )
                null
            }
        } catch (e: HttpException) {
            Log.d("Exception", "HttpException: ${e.message()}")
            null
        } catch (e: Exception) {
            Log.d("Exception", "General exception: ${e.message}")
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

}