package com.losrobotines.nuralign.feature_medication.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

class AddMedicationScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurAlignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewMedScreen()
                }
            }
        }
    }
}

@Composable
fun NewMedScreen(){
    SharedComponents().HalfCircleTop(title = "Agregar nueva medicación")
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp, 0.dp)
    ){
        item {
            Spacer(modifier = Modifier.height(110.dp))
        }
    }
}

@Preview
@Composable
fun NewMedElement(){
    var medicationName by remember { mutableStateOf("") }
    var medicationDose by remember { mutableStateOf("") }

    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {

        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.weight(0.7f)) {
            OutlinedTextField(
                value = medicationName,
                onValueChange = { medicationName = it },
                modifier = Modifier
                    .height(50.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                label ={ Text("Nombre", color = secondaryColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                )
            )
        }
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.weight(0.3f)) {
            OutlinedTextField(
                value = medicationDose,
                onValueChange = { medicationDose = it },
                modifier = Modifier
                    .height(50.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                label ={ Text("Dosis", color = secondaryColor) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                )
            )
        }
    }
}