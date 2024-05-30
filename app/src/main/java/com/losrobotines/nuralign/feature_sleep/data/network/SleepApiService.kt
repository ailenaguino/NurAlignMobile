package com.losrobotines.nuralign.feature_sleep.data.network

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.dto.MoodTrackerDto
import com.losrobotines.nuralign.feature_sleep.data.dto.SleepTrackerDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface SleepApiService {
    @Headers("Content-Type: application/json")
    @POST("sleepTracker")
    suspend fun insertSleepTrackerInfoIntoDatabase(@Body body: SleepTrackerDto)

    @GET("sleepTracker/{patientId}")
    suspend fun getSleepInfo(@Path("patientId") patientId: Int): SleepTrackerDto?


}