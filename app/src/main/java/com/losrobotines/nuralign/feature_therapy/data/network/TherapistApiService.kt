package com.losrobotines.nuralign.feature_therapy.data.network

import com.losrobotines.nuralign.feature_therapy.data.dto.TherapistDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TherapistApiService {

    @Headers("Content-Type: application/json")
    @POST("therapists")
    suspend fun insertTherapistInfo(@Body body: TherapistDto): Response<TherapistDto>

    @GET("patientTherapist/{patientId}")
    suspend fun getTherapistList(@Path("patientId") patientId: Short): List<TherapistDto?>

    @DELETE("patientTherapist/patient/{patientId}/therapist/{therapistId}")
    suspend fun deleteTherapistInfo(
        @Path("patientId") patientId: Short,
        @Path("therapistId") therapistId: Short
    ): Response<Unit>

    @PUT("therapists/{therapistId}")
    suspend fun updateTherapistInfo(
        @Path("therapistId") therapistId: Short,
        @Body body: TherapistDto
    ): Response<TherapistDto>

    @GET("therapists/{therapistId}")
    suspend fun getTherapistInfo(@Path("therapistId") therapistId: Short):TherapistDto
}