package com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.network

import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.dto.MoodTrackerDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface MoodTrackerApiService {

    @Headers("Content-Type: application/json")
    @POST("moodTracker")
    suspend fun insertMoodTrackerInfoIntoDatabase(@Body body: MoodTrackerDto)


    @GET("moodTracker/{patientId}/{date}")
    suspend fun getMoodTrackerInfo(
        @Path("patientId") patientId: Int,
        @Path("date") date: String
    ): MoodTrackerDto
}