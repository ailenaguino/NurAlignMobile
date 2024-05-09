package com.losrobotines.nuralign.feature_login.presentation.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_login.presentation.utils.LoginState
import com.losrobotines.nuralign.feature_login.presentation.screens.home.Home
import com.losrobotines.nuralign.feature_login.presentation.screens.signup.SignUpScreen
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen : ComponentActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurAlignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Verificar si el usuario está autenticado
                    val firebaseAuth = FirebaseAuth.getInstance()
                    val currentUser = firebaseAuth.currentUser

                    if (currentUser != null) {
                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val contextAplication = LocalContext.current.applicationContext
                        LoginScreen(contextAplication, viewModel)
                    }
                }
            }
        }

    }

    @SuppressLint("PrivateResource", "NotConstructor")
    @Composable
    fun LoginScreen(contextAplication: Context, viewModel: LoginViewModel) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        val loginFlow = viewModel.loginFlow.collectAsState()

        Image(
            painterResource(id = R.drawable.img),
            contentDescription = "fondo",
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(49.dp))
            Image(
                painterResource(id = R.drawable.logo),
                contentDescription = "LogoAPP",
                modifier = Modifier
                    .size(290.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 80.dp)
            )

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
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = mainColor,
                    focusedBorderColor = mainColor,
                    unfocusedBorderColor = mainColor,
                )
            )

            Spacer(modifier = Modifier.height(45.dp))

            SignInButton(email, password)

            Spacer(modifier = Modifier.height(30.dp))

            ClickableText(
                text = AnnotatedString("Olvidé mi contraseña"),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {

                },
                style = TextStyle(
                    fontSize = 17.sp,
                    fontFamily = FontFamily.Default,
                    color = mainColor
                )
            )

            Spacer(modifier = Modifier.height(55.dp))

            SignUpScreenButton(contextAplication)

        }

        loginFlow.value?.let {
            when (it) {
                is LoginState.Failure -> {
                    val context = LocalContext.current
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()

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
                        val intent = Intent(contextAplication, Home::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

    }

    @Composable
    fun SignUpScreenButton(contextApplication: Context) {
        TextButton(
            onClick = {
                val intent = Intent(contextApplication, SignUpScreen::class.java)
                startActivity(intent)
                finish()
            },
            border = BorderStroke(2.dp, secondaryColor),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Soy nuevo,\nquiero registrarme",
                    modifier = Modifier.fillMaxWidth(),
                    color = secondaryColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Add Person Icon",
                    modifier = Modifier.size(24.dp),
                    tint = secondaryColor
                )
            }
        }
    }

    @Composable
    private fun SignInButton(email: String, password: String) {
        Button(
            onClick = {
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(applicationContext, "Complete los campos", Toast.LENGTH_SHORT)
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



}