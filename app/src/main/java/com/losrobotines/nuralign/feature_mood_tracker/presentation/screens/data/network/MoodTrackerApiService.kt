package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.network

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.dto.MoodTrackerDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoodTrackerApiService {

    @Headers("Content-Type: application/json")
    @POST("moodTracker")
    suspend fun insertMoodTrackerInfo(@Body body: MoodTrackerDto) : Response<MoodTrackerDto>


    @GET("moodTracker/patient/{patientId}")
    suspend fun getMoodTrackerInfo(
        @Path("patientId") patientId: Int,
        @Query("effectiveDate") effectiveDate: String
    ): Response<MoodTrackerDto?>

    @PATCH("moodTracker/{patientId}")
    suspend fun updateMoodTrackerInfo(
        @Path("patientId") patientId: Short,
        @Query("effectiveDate") effectiveDate: String,
        @Body body: MoodTrackerDto
    ): Response<MoodTrackerDto>?
}