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
        return try {
            val dto = mapDomainToData(medicationTrackerInfo)
            val result = apiService.insertMedicationTrackerInfo(dto)
            result.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getMedicationTrackerData(
        patientMedicationId: Short,
        effectiveDate: String
    ): MedicationTrackerInfo? {
        val dto = apiService.getMedicationTrackerInfo(patientMedicationId, effectiveDate)
        return try {
            if (dto.isSuccessful) {
                mapDataToDomain(dto.body())
            } else {
                //Lo dejo así hasta que esté hecho que cuando no haya tracker en el día
                //la API devuelva 204. Por el momento devuelve 500.
                //Una vez que devuelva 204, el null pasa a devolver una Exception
                null
            }
        } catch (e: Exception) {
            throw Exception("Failed to get data")
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