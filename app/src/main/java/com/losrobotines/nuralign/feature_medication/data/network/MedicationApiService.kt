package com.losrobotines.nuralign.feature_medication.data.network

import com.losrobotines.nuralign.feature_medication.data.dto.MedicationDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface MedicationApiService {

    @Headers("Content-Type: application/json")
    @POST("medication")
    suspend fun insertMedicationInfoIntoDatabase(@Body body: MedicationDto)

    @GET("medication/{patientId}")
    suspend fun getMedicationList(@Path("patientId") patientId: Short): List<MedicationDto?>
}