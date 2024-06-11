package com.losrobotines.nuralign.feature_medication.data.providers

import android.os.Build
import androidx.annotation.RequiresApi
import com.losrobotines.nuralign.feature_medication.data.dto.MedicationTrackerDto
import com.losrobotines.nuralign.feature_medication.data.network.MedicationTrackerApiService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationTrackerInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationTrackerProvider
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MedicationTrackerProviderImpl @Inject constructor(private val apiService: MedicationTrackerApiService) :
    MedicationTrackerProvider {

    override suspend fun saveMedicationTrackerData(medicationTrackerInfo: MedicationTrackerInfo): Boolean {
        try {
            val dto = mapDomainToData(medicationTrackerInfo)
            val result = apiService.insertMedicationTrackerInfo(dto)
            return result.isSuccessful
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun getMedicationTrackerData(patientId: Short): MedicationTrackerInfo? {
        val dto = apiService.getMedicationTrackerInfo(patientId)
        return mapDataToDomain(dto)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTodaysTracker(patientId: Int, date: String): MedicationTrackerInfo? {
        try {
            val response = apiService.getTodaysTracker(patientId.toShort(), date)
            return mapDataToDomain(response?.find {
                it.effectiveDate == LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        && it.takenFlag.isNotBlank()
            })
        } catch (e: HttpException) {
            return null
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