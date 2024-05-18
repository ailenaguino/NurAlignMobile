package com.losrobotines.nuralign.feature_therapy.presentation.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor


@Composable
 fun AddTherapistScreenComponent(navController: NavController) {

    LazyVerticalGrid(columns = GridCells.Fixed(1),) {
    item{SharedComponents().HalfCircleTop(title = "Agregar terapeuta")}
    item{Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp, 0.dp)
    ) {
            Spacer(modifier = Modifier.height(24.dp))
            TherapistNameInput()

            Spacer(modifier = Modifier.height(8.dp))
            TherapistEmailInput()

            Spacer(modifier = Modifier.height(8.dp))
            TherapistPhoneInput()

            Spacer(modifier = Modifier.height(8.dp))
            WeeklyOrNo()
            Divider(color = secondaryColor, thickness = 2.dp)

            Spacer(modifier = Modifier.height(8.dp))
            SharedComponents().SelectDayButtons()

            Spacer(modifier = Modifier.height(32.dp))
            Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize()) {
                SaveButton(navController)

        }

    }}
}
}

@Composable
private fun TherapistNameInput() {
    var therapistName by remember { mutableStateOf("") }

    Box {
        OutlinedTextField(
            value = therapistName,
            onValueChange = { therapistName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(35.dp),
            label = { Text("Nombre del terapeuta", color = secondaryColor) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = secondaryColor
            )
        )
    }

}


@Composable
private fun TherapistEmailInput() {
    var therapistEmail by remember { mutableStateOf("") }

    Box {
        OutlinedTextField(
            value = therapistEmail,
            onValueChange = { therapistEmail = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(35.dp),
            label = { Text("Email del terapeuta", color = secondaryColor) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = secondaryColor
            )
        )
    }

}

@Composable
private fun TherapistPhoneInput() {
    var therapistPhone by remember { mutableStateOf("") }

    Box {
        OutlinedTextField(
            value = therapistPhone,
            onValueChange = { therapistPhone = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(35.dp),
            label = { Text("Teléfono del terapeuta", color = secondaryColor) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = secondaryColor
            )
        )
    }

}

@Composable
private fun WeeklyOrNo() {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.BottomStart, modifier = Modifier.weight(0.7f)) {
            Text(text = "¿Es semanal?", color = secondaryColor, fontSize = 16.sp)
        }

        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.weight(0.3f)) {
            var checked by remember { mutableStateOf(false) }
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                thumbContent = if (checked) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "si",
                            tint = secondaryColor,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                } else {
                    {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "no",
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = mainColor
                )
            )
        }
    }

}

@Composable
private fun SaveButton(navController: NavController) {
    val context = LocalContext.current
    Button(
        onClick = {
            navController.navigate(Routes.TherapyTrackerScreen.route)
        },
        colors = ButtonDefaults.buttonColors(containerColor = mainColor)
    ) {
        Text(text = "Guardar")
    }
}

