package com.losrobotines.nuralign.feature_medication.data.network

import com.losrobotines.nuralign.feature_medication.data.dto.MedicationTrackerDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MedicationTrackerApiService {

    @Headers("Content-Type: application/json")
    @POST("medicationTracker")
    suspend fun insertMedicationTrackerInfo(@Body medicationTrackerInfo: MedicationTrackerDto): Response<MedicationTrackerDto>

    @GET("medicationTracker/{patientMedicationId}")
    suspend fun getMedicationTrackerInfo(
        @Path("patientMedicationId") patientMedicationId: Short,
        @Query("effectiveDate") effectiveDate: String
    ): Response<MedicationTrackerDto?>

    @PATCH("medicationTracker/{patientMedicationId}")
    suspend fun updateMedicationTrackerInfo(
        @Path("patientMedicationId") patientMedicationId: Short,
        @Query("effectiveDate") effectiveDate: String,
        @Body medicationTrackerInfo: MedicationTrackerDto
    ): Response<MedicationTrackerDto>?

    @GET("medicationTracker")
    suspend fun getTodaysTracker(
        @Query("patientId") patientId: Short,
        @Query("date") date: String,
    ): Array<MedicationTrackerDto>?
}