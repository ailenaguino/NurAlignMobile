package com.losrobotines.nuralign.feature_settings.presentation.screens.personal_information

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.CustomDatePickerDialog
import com.losrobotines.nuralign.feature_login.presentation.utils.DateTransformation
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonalInformationScreenComponent(navigationController: NavHostController) {
    SharedComponents().HalfCircleTop("Ajustes")
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp, 0.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(110.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Mis datos personales", style = TextStyle(
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Default,
                        color = secondaryColor
                    )
                )
                Spacer(modifier = Modifier.height(40.dp))
                Divider(
                    color = secondaryColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Field(string = "Nombre")
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Field(string = "Apellido")
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Field(string = "Apodo")
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            BirthDate()

        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            SelectSexDropMenu()
        }

    }
}


@Composable
private fun Field(string: String) {
    var value by remember { mutableStateOf("") }

    Box {
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(35.dp),
            label = { Text(string, color = secondaryColor) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = secondaryColor
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun BirthDate() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val date = remember { mutableStateOf("") }
        val isOpen = remember { mutableStateOf(false) }

        OutlinedTextField(
            value = date.value,
            label = { Text("Fecha de Nacimiento"/*, color = mainColor*/) },
            onValueChange = { date.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 2.dp),
            shape = RoundedCornerShape(35.dp),
            singleLine = true,
            visualTransformation = DateTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = mainColor,
                focusedBorderColor = mainColor,
                unfocusedBorderColor = mainColor,
            )
        )
        IconButton(
            onClick = { isOpen.value = true }
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Calendar",
                tint = secondaryColor,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 1.dp, end = 11.dp, top = 2.dp)
                    .size(40.dp)
            )
        }

        if (isOpen.value) {
            CustomDatePickerDialog(
                onAccept = {
                    isOpen.value = false // close dialog
                    if (it != null) { // Set the date
                        date.value = Instant
                            .ofEpochMilli(it)
                            .atZone(ZoneId.of("UTC"))
                            .format(DateTimeFormatter.ofPattern("ddMMyyyy"))
                    }
                },
                onCancel = {
                    isOpen.value = false //close dialog
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SelectSexDropMenu() {
    val sexList = arrayOf("Femenino", "Masculino", "Otro")
    var expanded by remember { mutableStateOf(false) }
    var selectedSex by remember { mutableStateOf("Sexo") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Surface(
            color = Color.Transparent,
        ) {
            TextField(
                value = selectedSex,
                onValueChange = {},
                label = {
                    Text(text = "Sexo", color = mainColor) //
                },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                shape = RoundedCornerShape(35.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Transparent, // Color del cursor
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 1.dp, end = 1.dp)
                    .menuAnchor()
                    .border(
                        width = 1.dp,
                        color = mainColor,
                        shape = RoundedCornerShape(35.dp)
                    )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                sexList.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = it,
                            )
                        },
                        onClick = {
                            selectedSex = it
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}