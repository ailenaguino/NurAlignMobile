package com.losrobotines.nuralign.feature_companion.presentation.screens

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.losrobotines.nuralign.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompanionViewModel @Inject constructor() : ViewModel() {

    private val _currentStyle = MutableLiveData(R.drawable.robotin_bebe)
    var currentStyle = _currentStyle

    private val _isSelected = MutableLiveData(R.drawable.robotin_bebe)
    var isSelected = _isSelected


    fun setCurrentStyle(style: Int){
        _currentStyle.value = style
    }

    fun setIsSelected(selected: Int){
        _isSelected.value = selected
    }
}