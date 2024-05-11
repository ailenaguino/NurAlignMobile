package com.losrobotines.nuralign.feature_therapy.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import com.losrobotines.nuralign.ui.theme.secondaryColor

class TherapyTrackerScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurAlignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TherapyScreen()
                }
            }
        }
    }
}

@Composable
private fun TherapyScreen() {
    SharedComponents().HalfCircleTop(title = "Seguimiento de terapia")
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp, 0.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(150.dp))
            AddNewTherapySession()
        }
    }
}

@Preview
@Composable
private fun AddNewTherapySession() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .border(2.dp, secondaryColor, RoundedCornerShape(10.dp))
            .padding(24.dp, 8.dp, 8.dp, 8.dp)
    ) {
        Text(
            text = "Quiero agregar una nueva sesión de terapia",
            fontSize = 18.sp,
            color = secondaryColor,
            modifier = Modifier.weight(0.6f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = "arrow",
            tint = secondaryColor,
            modifier = Modifier
                .weight(0.1f)
                .align(Alignment.CenterVertically)
                .size(32.dp)
                .clickable { /*TODO*/ }
        )
    }
}