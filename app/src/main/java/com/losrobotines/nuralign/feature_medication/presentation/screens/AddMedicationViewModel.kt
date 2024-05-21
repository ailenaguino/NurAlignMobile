package com.losrobotines.nuralign.feature_medication.presentation.screens

import androidx.lifecycle.ViewModel
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import javax.inject.Inject

class AddMedicationViewModel @Inject constructor(private val repository: AuthRepository) :
    ViewModel() {
}