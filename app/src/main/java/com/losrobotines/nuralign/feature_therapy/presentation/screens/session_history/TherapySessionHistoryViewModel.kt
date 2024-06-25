package com.losrobotines.nuralign.feature_therapy.presentation.screens.session_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.losrobotines.nuralign.feature_login.domain.services.UserService
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapistInfo
import com.losrobotines.nuralign.feature_therapy.domain.models.TherapySessionInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TherapySessionHistoryViewModel @Inject constructor(
    private val userService: UserService
): ViewModel() {
    private val _sessionHistoryList = MutableLiveData<List<TherapySessionInfo>>()
    val sessionHistoryList: LiveData<List<TherapySessionInfo>> = _sessionHistoryList

    private val _sessionTherapist = MutableLiveData<TherapistInfo>()
    val sessionTherapist: LiveData<TherapistInfo> = _sessionTherapist

}