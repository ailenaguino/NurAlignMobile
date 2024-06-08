package com.losrobotines.nuralign.feature_sleep.data.network

import com.losrobotines.nuralign.feature_sleep.data.dto.SleepTrackerDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SleepApiService {
    @Headers("Content-Type: application/json")
    @POST("sleepTracker")
    suspend fun insertSleepTrackerInfoIntoDatabase(@Body body: SleepTrackerDto)

    @GET("sleepTracker/{patientId}")
    suspend fun getSleepInfo(@Path("patientId") patientId: Int): SleepTrackerDto?

    @GET("sleepTracker")
    suspend fun getTodaysTracker(
        @Query("patientId") patientId: Int,
        @Query("date") date: String,
    ): SleepTrackerDto?
}