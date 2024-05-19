package com.losrobotines.nuralign.feature_medication.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

@Composable
fun AddMedicationScreenComponent(navController: NavController) {
    val medicationCount = remember { mutableStateListOf<Int>() }
    Column {
        LazyVerticalGrid(columns = GridCells.Fixed(1)) {
            item {
                SharedComponents().HalfCircleTop(title = "Agregar nueva medicación")
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(medicationCount.size) {
                MedicationRow()
                Text(medicationCount[it].toString())
                if (medicationCount.size > 0) {
                    RemoveIcon(onClick = {
                        //Log.i("index line:", medicationCount[it].toString())
                        //borrar medicationCount[it]
                        //medicationCount.remove(it)
                        medicationCount.removeAt(it)
                    })
                }
            }
            item {
                AddIcon(onClick = {
                    medicationCount.add(medicationCount.size + 1)
                })
            }
        }
    }
}

@Preview
@Composable
fun AddMedicationElement() {
    var medicationName by remember { mutableStateOf("") }
    var medicationDose by remember { mutableStateOf("") }

    Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = medicationName,
                onValueChange = { medicationName = it },
                modifier = Modifier
                    .height(80.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                label = { Text("Nombre", color = secondaryColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                )
            )
        }
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(0.3f)
                .padding(horizontal = 4.dp)
        ) {
            OutlinedTextField(
                value = medicationDose,
                onValueChange = { medicationDose = it },
                modifier = Modifier
                    .height(80.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                label = { Text("Dosis", color = secondaryColor) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = secondaryColor
                )
            )
        }
    }
}

@Composable
fun AddIcon(onClick: () -> Unit = {}) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Icon(
            Icons.Filled.Add,
            tint = secondaryColor,
            contentDescription = "agregar",
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp)
                .align(Alignment.TopCenter)
                .clickable { onClick() }
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
}

@Composable
fun RemoveIcon(onClick: () -> Unit = {}) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Icon(
            Icons.Filled.Remove,
            tint = secondaryColor,
            contentDescription = "quitar",
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp)
                .align(Alignment.TopCenter)
                .clickable { onClick() }
        )
    }
}

@Preview
@Composable
fun Optional() {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.BottomStart, modifier = Modifier.weight(0.7f)) {
            Text(text = "Opcional", color = secondaryColor, fontSize = 16.sp)
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
fun MedicationRow() {
    AddMedicationElement()
    Spacer(modifier = Modifier.height(8.dp))

    SharedComponents().SelectDayButtons()
    Spacer(modifier = Modifier.height(8.dp))

    Optional()
    Divider(color = secondaryColor, thickness = 2.dp)
}
