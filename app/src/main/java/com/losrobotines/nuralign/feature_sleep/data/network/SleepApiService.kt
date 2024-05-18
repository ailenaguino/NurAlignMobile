package com.losrobotines.nuralign.feature_sleep.data.network

import com.losrobotines.nuralign.feature_sleep.data.models.SleepTrackerDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SleepApiService {
    @Headers("Content-Type: application/json")
    @POST("api/sleepTracker")
    suspend fun insertSleepTrackerInfoIntoDatabase(@Body sleepTrackerDto: SleepTrackerDto) : Call<*>

}