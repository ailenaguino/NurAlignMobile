package com.losrobotines.nuralign.feature_weekly_summary.data

import com.losrobotines.nuralign.feature_medication.data.dto.MedicationDto
import com.losrobotines.nuralign.feature_medication.data.dto.MedicationTrackerDto
import com.losrobotines.nuralign.feature_mood_tracker.data.dto.MoodTrackerDto
import com.losrobotines.nuralign.feature_sleep.data.dto.SleepTrackerDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeeklySummaryApiService {


    @GET("moodTracker/patient/{patientId}")
    suspend fun getMoodTrackerByDate(
        @Path("patientId") patientId: Short,
        @Query("effectiveDate") effectiveDate: String,
    ): Response<MoodTrackerDto?>

    @GET("sleepTracker/patient/{patientId}")
    suspend fun getSleepTrackerByDate(
        @Path("patientId") patientId: Short,
        @Query("effectiveDate") effectiveDate: String,
    ): Response<SleepTrackerDto?>


    @GET("medicationTracker/{patientMedicationId}")
    suspend fun getMedicationTrackerInfo(
        @Path("patientMedicationId") patientMedicationId: Short,
        @Query("effectiveDate") effectiveDate: String
    ): Response<MedicationTrackerDto?>

    @GET("medications/patient/{patientId}")
    suspend fun getMedicationList(@Path("patientId") patientId: Short): List<MedicationDto?>



}