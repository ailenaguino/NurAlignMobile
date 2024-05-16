package com.losrobotines.nuralign.feature_therapy.presentation.screens

import android.content.Intent
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor




@Composable
 fun TherapyScreen(navController: NavController) {
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
                    AddNewTherapistButton(navController)
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
private fun AddNewTherapistButton(navController: NavController) {
    val context = LocalContext.current
    Button(
        onClick = {
            navController.navigate(Routes.AddTherapyScreen.route)
        },
        colors = ButtonDefaults.buttonColors(containerColor = mainColor)
    ) {
        Text(text = "Agregar nuevo terapeuta", modifier = Modifier.padding(horizontal = 16.dp))
    }
}
