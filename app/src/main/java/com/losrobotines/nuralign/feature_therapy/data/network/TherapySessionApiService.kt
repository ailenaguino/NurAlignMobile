package com.losrobotines.nuralign.feature_therapy.data.network

import com.losrobotines.nuralign.feature_therapy.data.dto.TherapySessionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TherapySessionApiService {

    @Headers("Content-Type: application/json")
    @POST("therapySession")
    suspend fun insertTherapySessionInfo(@Body body: TherapySessionDto): Response<TherapySessionDto>

    @GET("therapySession/patient/{patientId}/therapist/{therapistId}")
    suspend fun getTherapySessionList(
        @Path("patientId") patientId: Short,
        @Path("therapistId") therapistId: Short
    ): List<TherapySessionDto>

    @GET("therapySession/{sessionTherapyId}")
    suspend fun getTherapySessionInfo(@Path("sessionTherapyId") sessionTherapyId: Short): TherapySessionDto

    @PATCH("therapySession/patient/{patientId}")
    suspend fun updateTherapySessionInfo(
        @Path("patientId") patientId: Short,
        @Query("therapistId") therapistId: Short,
        @Query("effectiveDate") effectiveDate: String,
        @Body body: TherapySessionDto
    ): Response<TherapySessionDto>
}