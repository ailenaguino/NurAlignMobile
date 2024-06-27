package com.losrobotines.nuralign.feature_login.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor

@Composable
fun ForgottenPasswordScreen(navController: NavController, viewModel: LoginViewModel) {
    val message by viewModel.messageResetPass.observeAsState("")
    LazyVerticalGrid(
        columns = GridCells.Fixed(1)
    ) {
        item {
            SharedComponents().HalfCircleTop("")
        }
        item {
            ForgottenPasswordForm(viewModel)
        }
        item {
            if(message.isNotEmpty()){
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        message,
                        color = secondaryColor,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Composable
fun ForgottenPasswordForm(viewModel: LoginViewModel){
    val email by viewModel.emailForgottenPassword.observeAsState("")
    Column(
        Modifier
            .fillMaxSize()
            .padding(
                horizontal =
                32.dp, vertical = 16.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿Olvidaste tu contraseña? Colocá tu email y te enviaremos un correo para que la restaures",
            color = secondaryColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.setEmail(it) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(35.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            label = { Text("Ingrese su email", color = mainColor) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = mainColor,
                unfocusedBorderColor = mainColor,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonSendEmail(viewModel)
    }
}

@Composable
private fun ButtonSendEmail(
    viewModel: LoginViewModel
) {
    val email by viewModel.emailForgottenPassword.observeAsState("")
    Button(
        onClick = { if(!email.isNullOrEmpty()) viewModel.sendEmailForNewPassword(email) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 80.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = secondaryColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text("Enviar")
    }
}