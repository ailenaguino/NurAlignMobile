package com.losrobotines.nuralign.feature_medication.data.network

import com.losrobotines.nuralign.feature_medication.data.dto.MedicationDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MedicationApiService {

    @Headers("Content-Type: application/json")
    @POST("medications")
    suspend fun insertMedicationInfoIntoDatabase(@Body body: MedicationDto): Response<MedicationDto>

    @GET("medications/patient/{patientId}")
    suspend fun getMedicationList(@Path("patientId") patientId: Short): List<MedicationDto?>

    @PATCH("medications/{patientMedicationId}")
    suspend fun deleteMedicationInfo(
        @Path("patientMedicationId") patientMedicationId: Short,
        @Body body: MedicationDto
    ): Response<Unit>

    @PATCH("medications/patient/{patientId}")
    suspend fun updateMedicationInfo(
        @Path("patientId") patientId: Short,
        @Body body: MedicationDto
    ): Response<MedicationDto>
}