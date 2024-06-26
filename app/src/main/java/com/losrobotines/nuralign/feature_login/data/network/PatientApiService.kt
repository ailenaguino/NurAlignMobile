package com.losrobotines.nuralign.feature_login.data.network

import com.google.gson.annotations.SerializedName
import com.losrobotines.nuralign.feature_login.data.dto.PatientDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PatientApiService {

    @Headers("Content-Type: application/json")
    @POST("patients")
    suspend fun insertPatientInfoIntoDatabase(@Body body: PatientDto): PatientId

}

data class PatientId(@SerializedName("id") val patientId: Short)