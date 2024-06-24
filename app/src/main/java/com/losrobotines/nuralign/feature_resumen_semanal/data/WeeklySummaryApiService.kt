package com.losrobotines.nuralign.feature_resumen_semanal.data

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.dto.MoodTrackerDto
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


    /*

        @GET("moodTracker")
        suspend fun getTrackerByDate(
            @Query("patientId") patientId: Int,
            @Query("date") date: String,
        ): Array<MoodTrackerDto>?

     */


}