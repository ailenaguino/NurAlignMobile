package com.losrobotines.nuralign.feature_sleep.data.network

import com.losrobotines.nuralign.feature_sleep.data.dto.SleepTrackerDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SleepApiService {
    @Headers("Content-Type: application/json")
    @POST("sleepTracker")
    suspend fun insertSleepTrackerInfo(@Body body: SleepTrackerDto): Response<SleepTrackerDto>

    //sleepTracker/patient/25?effectiveDate=2024-06-18
    @GET("sleepTracker/patient/{patientId}")
    suspend fun getSleepInfo(
        @Path("patientId") patientId: Int,
        @Query("effectiveDate") effectiveDate: String,
    ): Response<SleepTrackerDto?>

    @PATCH("sleepTracker/{patientId}")
    suspend fun updateSleepTrackerInfo(
        @Path("patientId") patientId: Short,
        @Query("effectiveDate") effectiveDate: String,
        @Body body: SleepTrackerDto
    ): Response<SleepTrackerDto>?
}