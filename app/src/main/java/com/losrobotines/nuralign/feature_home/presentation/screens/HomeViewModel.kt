package com.losrobotines.nuralign.feature_home.presentation.screens

import androidx.lifecycle.ViewModel
import com.losrobotines.nuralign.feature_home.domain.usecases.ResetDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resetDatabaseUseCase: ResetDatabaseUseCase
) : ViewModel() {
    fun resetDatabase() {
        resetDatabaseUseCase()
    }
}