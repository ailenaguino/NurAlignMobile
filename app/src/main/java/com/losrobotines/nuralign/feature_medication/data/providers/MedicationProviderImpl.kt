package com.losrobotines.nuralign.feature_medication.data.providers

import com.losrobotines.nuralign.feature_medication.data.dto.MedicationDto
import com.losrobotines.nuralign.feature_medication.data.network.MedicationApiService
import com.losrobotines.nuralign.feature_medication.domain.models.MedicationInfo
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import javax.inject.Inject

class MedicationProviderImpl @Inject constructor(private val apiService: MedicationApiService) :
    MedicationProvider {

    override suspend fun saveMedicationInfo(medicationInfo: MedicationInfo?): Boolean {
        return try {
            if (medicationInfo != null) {
                val dto = mapDomainToData(medicationInfo).copy(enabledFlag = "Y")
                apiService.insertMedicationInfo(dto)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getMedicationList(patientId: Short): List<MedicationInfo?> {
        val dto = apiService.getMedicationList(patientId)
        return mapDataToDomain(dto)
    }

    override suspend fun updateMedicationInfo(newMedicationInfo: MedicationInfo?): Boolean {
        try {
            val dto = mapDomainToData(newMedicationInfo!!)
            apiService.updateMedicationInfo(newMedicationInfo.patientId, dto)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun deleteMedicationInfo(medicationInfo: MedicationInfo): Boolean {
        try {
            val dto = mapDomainToData(medicationInfo).copy(enabledFlag = "N")
            apiService.deleteMedicationInfo(medicationInfo.patientId, dto)
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
            flag = medicationInfo.medicationOptionalFlag
        )
    }

    private fun mapDataToDomain(dto: List<MedicationDto?>): List<MedicationInfo?> {
        val list = mutableListOf<MedicationInfo?>()
        dto.let {
            for (med in it) {
                if (med != null) {
                    if (med.enabledFlag == "Y") {
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
        }
        return list
    }
}