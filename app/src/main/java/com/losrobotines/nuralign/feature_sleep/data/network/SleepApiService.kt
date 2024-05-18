package com.losrobotines.nuralign.feature_sleep.data.network

import com.losrobotines.nuralign.feature_sleep.data.models.SleepTrackerDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SleepApiService {
    @Headers("Content-Type: application/json")
    @POST("sleepTracker")
    suspend fun insertSleepTrackerInfoIntoDatabase(@Body body: SleepTrackerDto)

}