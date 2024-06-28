package com.losrobotines.nuralign.feature_medication.data.providers

import android.util.Log
import com.losrobotines.nuralign.feature_medication.data.dto.MedicationDto
import com.losrobotines.nuralign.feature_medication.data.network.MedicationApiService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import javax.inject.Inject

class MedicationProviderImpl @Inject constructor(private val apiService: MedicationApiService) :
    MedicationProvider {

    override suspend fun saveMedicationInfo(medicationInfo: MedicationInfo?): Boolean {
        try {
            val dto = mapDomainToData(medicationInfo!!)
            apiService.insertMedicationInfoIntoDatabase(dto)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun getMedicationList(patientId: Short): List<MedicationInfo?> {
        val dto = apiService.getMedicationList(patientId)
        Log.d("MedicationProvider", "DtO Obtenido: $dto")
        return mapDataToDomain(dto)
    }

    override suspend fun updateMedicationInfo(newMedicationInfo: MedicationInfo?): Boolean {
        try {
            val dto = mapDomainToData(newMedicationInfo!!)
            apiService.updateMedicationInfo(dto.patientMedicationId!!, dto)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun deleteMedicationInfo(patientMedicationId: Short): Boolean {
        try {
            apiService.deleteMedicationInfo(patientMedicationId)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun mapDomainToData(medicationInfo: MedicationInfo): MedicationDto {
        return MedicationDto(
            patientMedicationId = medicationInfo.patientMedicationId,
            patientId = medicationInfo.patientId,
            name = medicationInfo.medicationName,
            grammage = medicationInfo.medicationGrammage,
            //medicationDays = 10,
            flag = medicationInfo.medicationOptionalFlag
        )
    }

    private fun mapDataToDomain(dto: List<MedicationDto?>): List<MedicationInfo?> {
        val list = mutableListOf<MedicationInfo?>()
        dto.let {
            for (med in it) {
                if (med != null) {
                    list.add(
                        MedicationInfo(
                            patientMedicationId = med.patientMedicationId,
                            patientId = med.patientId,
                            medicationName = med.name,
                            medicationGrammage = med.grammage,
                            medicationOptionalFlag = med.flag
                        )
                    )
                }
            }
        }
        return list
    }
}