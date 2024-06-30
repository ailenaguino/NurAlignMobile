package com.losrobotines.nuralign.feature_login.domain.services

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationTrackerProvider
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapistProvider
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapySessionProvider
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

private const val USER = "users"
private const val ID = "id"

class UserService @Inject constructor(
    private val authRepository: AuthRepository,
    private val medicationProvider: MedicationProvider,
    private val medicationTrackerProvider: MedicationTrackerProvider,
    private val therapistProvider: TherapistProvider,
    private val therapySessionProvider: TherapySessionProvider
) {
    suspend fun getPatientId(): Result<Short?> {
        return try {
            val currentUser = authRepository.getCurrentUserId()
            val doc = Firebase.firestore.collection(USER).document(currentUser!!)
            val patientId = doc.get().await().getLong(ID)

            Result.success(patientId!!.toShort())
        } catch (e: Exception) {
            Result.failure(Exception("Failed to get patient id: ${e.message}"))
        }
    }

    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
    }

    suspend fun getMedicationList(id: Short): Result<List<MedicationInfo?>> {
        return try {
            Result.success(medicationProvider.getMedicationList(id))
        } catch (e: Exception) {
            Result.failure(Exception("Failed to get medication list: ${e.message}"))
        }
    }

    suspend fun getMedicationTrackerData(
        id: Short,
        effectiveDate: String
    ): Result<MedicationTrackerInfo?> {
        return try {
            val result = medicationTrackerProvider.getMedicationTrackerData(id, effectiveDate)
            if (result.isSuccess) {
                result
            } else if (result.isFailure
                && result.exceptionOrNull()?.message == "End of input at line 1 column 1 path \$"
            ) {
                Result.success(null)
            } else {
                Result.failure(Exception("No tracker data for medication $id on $effectiveDate"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Failed to get medication tracker data: ${e.message}"))
        }
    }

    suspend fun checkExistingTracker(id: Short, effectiveDate: String): Boolean {
        return try {
            val result = medicationTrackerProvider.getMedicationTrackerData(id, effectiveDate)

            result.isSuccess
        } catch (e: Exception) {
            Log.e("UserService", e.message!!)
            false
        }
    }

    suspend fun getTherapistList(id: Short): Result<List<TherapistInfo?>> {
        return try {
            Result.success(therapistProvider.getTherapistList(id))
        } catch (e: Exception) {
            Result.failure(Exception("Failed to get therapist list: ${e.message}"))
        }
    }

    suspend fun getTherapySessionList(
        patientId: Short,
        therapistId: Short
    ): Result<List<TherapySessionInfo?>> {
        return try {
            Result.success(therapySessionProvider.getTherapySessionList(patientId, therapistId))
        } catch (e: Exception) {
            Result.failure(Exception("Failed to get therapy session list: ${e.message}"))
        }
    }

    suspend fun getTherapySessionInfo(id: Short): Result<TherapySessionInfo?> {
        return try {
            Result.success(therapySessionProvider.getTherapySessionInfo(id))
        } catch (e: Exception) {
            Result.failure(Exception("Failed to get therapy session info: ${e.message}"))
        }
    }
}