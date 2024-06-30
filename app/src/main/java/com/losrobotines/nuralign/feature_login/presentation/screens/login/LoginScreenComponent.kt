package com.losrobotines.nuralign.feature_login.presentation.screens.login


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_login.presentation.utils.LoginState
import com.losrobotines.nuralign.navigation.MainActivity
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import kotlinx.coroutines.delay


@SuppressLint("PrivateResource", "NotConstructor")
@Composable
fun LoginScreenComponent(navController: NavController, viewModel: LoginViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val contextAplication = LocalContext.current.applicationContext

    val message by viewModel.message.observeAsState("")
    val loginFlow = viewModel.loginFlow.collectAsState()


    SharedComponents().HalfCircleTop(title = "")
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(49.dp))
        Image(
            painterResource(id = R.drawable.logo_white_background),
            contentDescription = "LogoAPP",
            modifier = Modifier
                .size(290.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 80.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(35.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                label = { Text("Ingrese su email", color = mainColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = mainColor,
                    unfocusedBorderColor = mainColor,
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(35.dp),
                label = { Text("Ingrese su contraseña", color = mainColor) },
                singleLine = true,
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible)
                        "Ocultar contraseña" else "Mostrar contraseña"

                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(imageVector = image, description, tint = secondaryColor)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = mainColor,
                    focusedBorderColor = mainColor,
                    unfocusedBorderColor = mainColor,
                )
            )
            if(message.isNotEmpty()){
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Debes verificar tu email para poder iniciar sesión",
                        color = Color.Red,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            Spacer(modifier = Modifier.height(45.dp))

            SignInButton(navController,email, password,viewModel,contextAplication)

            Spacer(modifier = Modifier.height(30.dp))

            ClickableText(
                text = AnnotatedString("Olvidé mi contraseña"),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {navController.navigate(Routes.ForgottenPasswordScreen.route)
                },
                style = TextStyle(
                    fontSize = 17.sp,
                    fontFamily = FontFamily.Default,
                    color = mainColor
                )
            )

            Spacer(modifier = Modifier.height(55.dp))

            SignUpScreenButton(navController)

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    val context = LocalContext.current
    loginFlow.value?.let {
        when (it) {
            is LoginState.Failure -> {
                Toast.makeText(context, "Ha surgido un error", Toast.LENGTH_SHORT).show()
            }

            LoginState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(68.dp),
                        color = secondaryColor,
                        strokeWidth = 5.dp
                    )
                }
            }

            is LoginState.Success -> {
                LaunchedEffect(Unit) {
                    //navController.navigate(Routes.HomeScreen.route)
                    context.startActivity(Intent(context, MainActivity::class.java))
                }
            }

            LoginState.EmailNotVerified -> {}
        }
    }

}

@Composable
fun SignUpScreenButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.SignUpScreen.route)
        },
        border = BorderStroke(2.dp, secondaryColor),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(0.85f)) {
                Text(text = "Soy nuevo,\nquiero registrarme", color = secondaryColor)
            }
            Column(Modifier.weight(0.15f), horizontalAlignment = Alignment.End) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "registrarse",
                    tint = secondaryColor
                )
            }
        }
    }

}

@Composable
private fun SignInButton(
    navController: NavController,
    email: String,
    password: String,
    viewModel: LoginViewModel,
    contextAplication: Context
) {
    Button(
        onClick = {
            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(contextAplication, "Complete los campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.login(email, password)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 80.dp, end = 80.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = secondaryColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text("Iniciar sesión")
    }
}