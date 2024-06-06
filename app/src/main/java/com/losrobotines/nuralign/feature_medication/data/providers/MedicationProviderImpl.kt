package com.losrobotines.nuralign.feature_medication.data.providers

import android.util.Log
import com.losrobotines.nuralign.feature_medication.data.dto.MedicationDto
import com.losrobotines.nuralign.feature_medication.data.network.MedicationApiService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationRepository
import javax.inject.Inject

class MedicationProviderImpl @Inject constructor(private val apiService: MedicationApiService) :
    MedicationRepository {

    override suspend fun saveMedicationInfo(medicationInfo: List<MedicationInfo?>) {
        try {
            for (med in medicationInfo) {
                val dto = mapDomainToData(med!!)
                Log.d("MedicationRepository", "DtO Generado: $dto")
                apiService.insertMedicationInfoIntoDatabase(dto)
                Log.d("MedicationRepository", "$dto inserta")
            }
            Log.d("MedicationRepository", "Successfully saved MoodTrackerInfo")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("MedicationRepository", "Error saving MedicationInfo", e)
        }
    }

    override suspend fun getMedicationList(patientId: Short): MutableList<MedicationInfo?> {
        val dto = apiService.getMedicationList(patientId)
        Log.d("MedicationRepository", "DtO Obtenido: $dto")
        return mapDataToDomain(dto)
    }

    private fun mapDomainToData(medicationInfo: MedicationInfo): MedicationDto {
        return MedicationDto(
            patientId = medicationInfo.patientId,
            name = medicationInfo.medicationName,
            grammage = medicationInfo.medicationGrammage,
            //medicationDays = 10,
            flag = medicationInfo.medicationOptionalFlag
        )
    }

    private fun mapDataToDomain(dto: List<MedicationDto?>): MutableList<MedicationInfo?> {
        val list = mutableListOf<MedicationInfo?>()
        dto.let {
            for (med in it) {
                if (med != null) {
                    list.add(
                        MedicationInfo(
                            patientId = med.patientId,
                            medicationName = med.name,
                            medicationGrammage = med.grammage,
                            //medicationDays = med.medicationDays,
                            medicationOptionalFlag = med.flag
                        )
                    )
                }
            }
        }
        return list
    }
}