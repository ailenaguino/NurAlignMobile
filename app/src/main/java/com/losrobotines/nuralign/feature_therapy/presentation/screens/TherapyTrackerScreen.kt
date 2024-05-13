package com.losrobotines.nuralign.feature_therapy.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import com.losrobotines.nuralign.ui.theme.mainColor
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

@Preview
@Composable
private fun TherapyScreen() {
    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        item { SharedComponents().HalfCircleTop(title = "Seguimiento de terapia") }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp, 0.dp)
            ) {

                Spacer(modifier = Modifier.height(24.dp))
                AddNewTherapySession()
                Spacer(modifier = Modifier.height(40.dp))
                MyTherapistsTitle()
                TherapistElement()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    AddNewTherapistButton()
                }

            }
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

@Preview
@Composable
private fun MyTherapistsTitle() {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Text(text = "Mis terapeutas", fontSize = 24.sp, color = secondaryColor)
        Divider(color = secondaryColor, thickness = 2.dp)
    }
}

@Preview
@Composable
private fun TherapistElement() {
    val therapistName = "Dra. Marcela Marcelez"
    Column(modifier = Modifier.padding(8.dp, 24.dp)) {
        Row() {
            Text(
                text = therapistName,
                fontSize = 20.sp,
                color = mainColor,
                //fontWeight = FontWeight.Light,
                modifier = Modifier.weight(0.6f)
            )
            Box(
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .weight(0.2f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "editar",
                    tint = mainColor,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { /*TODO*/ }
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .weight(0.2f)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "ir al historial",
                    tint = mainColor,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { /*TODO*/ }
                )
            }
        }

        Divider(
            color = mainColor,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}


@Preview
@Composable
private fun AddNewTherapistButton() {
    val context = LocalContext.current
    Button(
        onClick = {
            context.startActivity(Intent(context, AddTherapistScreen::class.java))
        },
        colors = ButtonDefaults.buttonColors(containerColor = mainColor)
    ) {
        Text(text = "Agregar nuevo terapeuta", modifier = Modifier.padding(horizontal = 16.dp))
    }
}
