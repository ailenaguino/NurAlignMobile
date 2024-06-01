package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.network

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.dto.MoodTrackerDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoodTrackerApiService {

    @Headers("Content-Type: application/json")
    @POST("moodTracker")
    suspend fun insertMoodTrackerInfoIntoDatabase(@Body body: MoodTrackerDto)


    @GET("moodTracker/{patientId}")
    suspend fun getMoodTrackerInfo(
        @Path("patientId") patientId: Int,
    ): MoodTrackerDto?

    @GET("moodTracker")
    suspend fun getTodaysTracker(
        @Query("patientId") patientId: Int,
        @Query("date") date: String,
    ): MoodTrackerDto?
}