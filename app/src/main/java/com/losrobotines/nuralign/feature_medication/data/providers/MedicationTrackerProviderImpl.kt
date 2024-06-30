package com.losrobotines.nuralign.feature_medication.data.providers

import android.util.Log
import com.losrobotines.nuralign.feature_medication.data.dto.MedicationTrackerDto
import com.losrobotines.nuralign.feature_medication.data.network.MedicationTrackerApiService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationTrackerProvider
import javax.inject.Inject

class MedicationTrackerProviderImpl @Inject constructor(private val apiService: MedicationTrackerApiService) :
    MedicationTrackerProvider {

    override suspend fun saveMedicationTrackerData(medicationTrackerInfo: MedicationTrackerInfo): Result<Unit> {
        return try {
            val dto = mapDomainToData(medicationTrackerInfo)
            val result = apiService.insertMedicationTrackerInfo(dto)
            if (result.isSuccessful){
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to save medication tracker data"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMedicationTrackerData(
        patientMedicationId: Short,
        effectiveDate: String
    ): Result<MedicationTrackerInfo?> {
        return try {
            val dto = apiService.getMedicationTrackerInfo(patientMedicationId, effectiveDate)

            if (dto.isSuccessful) {
                Result.success(mapDataToDomain(dto.body()))
            } else if (dto.body()?.equals(null) == true) {
                Log.e("MedicationTrackerProvider", "entro en body null")
                Result.success(null)
            } else {
                Result.failure(Exception("Failed to get medication tracker data"))
            }
        } catch (e: Exception) {
            Log.e("MedicationTrackerProvider", e.message!!)
            Result.failure(Exception(e.message))
        }
    }

    override suspend fun updateMedicationTrackerData(medicationTrackerInfo: MedicationTrackerInfo): Boolean {
        return try {
            val dto = mapDomainToData(medicationTrackerInfo)
            val result =
                apiService.updateMedicationTrackerInfo(
                    dto.patientMedicationId,
                    dto.effectiveDate,
                    dto
                )
            result!!.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    private fun mapDataToDomain(dto: MedicationTrackerDto?): MedicationTrackerInfo? {
        return dto?.let {
            MedicationTrackerInfo(
                patientMedicationId = it.patientMedicationId,
                effectiveDate = it.effectiveDate,
                takenFlag = it.takenFlag
            )
        }
    }

    private fun mapDomainToData(medicationTrackerInfo: MedicationTrackerInfo): MedicationTrackerDto {
        return MedicationTrackerDto(
            patientMedicationId = medicationTrackerInfo.patientMedicationId,
            effectiveDate = medicationTrackerInfo.effectiveDate,
            takenFlag = medicationTrackerInfo.takenFlag
        )
    }
}