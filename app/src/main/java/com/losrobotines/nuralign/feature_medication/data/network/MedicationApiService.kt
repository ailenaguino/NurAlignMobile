package com.losrobotines.nuralign.feature_medication.data.network

import com.losrobotines.nuralign.feature_medication.data.dto.MedicationDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MedicationApiService {

    @Headers("Content-Type: application/json")
    @POST("medication")
    suspend fun insertMedicationInfoIntoDatabase(@Body body: MedicationDto)

    @GET("medication")
    suspend fun getMedicationList(@Query("patientId") patientId: Short): List<MedicationDto?>

/*
    @GET("medication/{patientId}")
    suspend fun getMedicationTracker(@Path("patientId") patientId: Short,
                                    @Query("effectiveDate") effectiveDate: String): MedicationTrackerDto

 */

}