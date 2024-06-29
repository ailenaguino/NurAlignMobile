package com.losrobotines.nuralign.feature_login.presentation.screens.signup


import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.containerColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import com.losrobotines.nuralign.feature_login.presentation.utils.DateTransformation
import com.losrobotines.nuralign.feature_login.presentation.utils.LoginState
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.preferences.PreferencesManager
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

const val USER_NAME = "user_name"
const val USER_SEX = "user_sex"
const val COMPANION = "companion"
const val FEMENINO = "Femenino"
const val MASCULINO = "Masculino"
const val OTRO = "Otro"

@SuppressLint("NotConstructor")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreenComponent(navController: NavController, viewModel: SignUpViewModel) {
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var userFirstName by remember { mutableStateOf("") }
    var userLastName by remember { mutableStateOf("") }
    var userBirthDate by remember { mutableStateOf("") }
    var userSex by remember { mutableStateOf("Seleccione su sexo") }

    val message by viewModel.message.observeAsState("")

    val signupFlow = viewModel.signupFlow.collectAsState()

    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(1)
    ) {
        item {
            SharedComponents().HalfCircleTop(title = "Registro")
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = userEmail,
                    onValueChange = { userEmail = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(35.dp),
                    label = { Text("Email") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = mainColor,
                        unfocusedBorderColor = mainColor,
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = userPassword,
                    onValueChange = { userPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(35.dp),
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(35.dp),
                    label = { Text("Confirmar contraseña") },
                    singleLine = true,
                    visualTransformation = if (confirmPasswordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (confirmPasswordVisible)
                            Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (confirmPasswordVisible)
                            "Ocultar contraseña" else "Mostrar contraseña"

                        IconButton(
                            onClick = { confirmPasswordVisible = !confirmPasswordVisible },
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
                MatchingPassword(userPassword, confirmPassword)

                if ((userPassword == confirmPassword) && confirmPassword.isNotEmpty()) {
                    PasswordIsValid(password = userPassword)
                }

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = userFirstName,
                    onValueChange = { userFirstName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(35.dp),
                    label = { Text("Nombre") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = mainColor,
                        focusedBorderColor = mainColor,
                        unfocusedBorderColor = mainColor,
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = userLastName,
                    onValueChange = { userLastName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(35.dp),
                    label = { Text("Apellido") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = mainColor,
                        focusedBorderColor = mainColor,
                        unfocusedBorderColor = mainColor,
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                userBirthDate = SelectBirthday()

                Spacer(modifier = Modifier.height(13.dp))

                userSex = selectSexDropMenu()

                Spacer(modifier = Modifier.height(38.dp))

                Button(
                    onClick = {
                        if (validatePassword(userPassword, confirmPassword) && validateFields(
                                userEmail,
                                userSex,
                                userFirstName,
                                userLastName,
                                userBirthDate
                            )
                        ) {
                            viewModel.signup(
                                userEmail,
                                userPassword,
                                userFirstName,
                                userLastName,
                                userBirthDate,
                                userSex
                            )
                        } else {
                            Toast.makeText(context, "Coloque datos válidos", Toast.LENGTH_SHORT)
                                .show()
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
                    Text("Creá tu cuenta")
                }

                Spacer(modifier = Modifier.height(30.dp))

                ClickableText(
                    text = AnnotatedString("Ya estoy registrado"),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        navController.navigate(Routes.LoginScreen.route)
                    },
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontFamily = FontFamily.Default,
                        color = mainColor
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

            }
        }
    }

    signupFlow.value?.let {
        when (it) {
            is LoginState.Failure -> Toast.makeText(context, "Ha surgido un error", Toast.LENGTH_SHORT).show()

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
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                preferencesManager.saveData(USER_NAME, userFirstName)
                preferencesManager.saveData(USER_SEX, userSex)
                preferencesManager.saveIntData(COMPANION, R.drawable.robotin_bebe)
                LaunchedEffect(Unit) {
                    navController.navigate(Routes.LoginScreen.route)
                }
            }

            LoginState.EmailNotVerified -> Toast.makeText(context, "Email no verificado", Toast.LENGTH_LONG).show()
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectBirthday(): String {
    val date = remember { mutableStateOf("") }
    val isOpen = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {

        OutlinedTextField(
            value = date.value,
            label = { Text("Fecha de Nacimiento") },
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
                unfocusedBorderColor = mainColor
            ),
            placeholder = { Text("Seleccioná la fecha en el calendario") },
            readOnly = true
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
    return date.value
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit,
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = { onAccept(state.selectedDateMillis) }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = state, dateValidator = { pickerDateMillis ->
            val pickerDate =
                pickerDateMillis.let {
                    Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate()
                }
            pickerDate < LocalDate.now()
        })
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun selectSexDropMenu(): String {
    val sexList = arrayOf(FEMENINO, MASCULINO, OTRO)
    var expanded by remember { mutableStateOf(false) }
    var selectedSex by remember { mutableStateOf("Seleccioná tu sexo") }

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
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    cursorColor = Color.Transparent,
                    focusedIndicatorColor = mainColor,
                    unfocusedIndicatorColor = mainColor,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = secondaryColor,
                    unfocusedTextColor = secondaryColor,
                    disabledTextColor = secondaryColor
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
    return selectedSex
}

@Composable
private fun MatchingPassword(password: String, confirmPassword: String) {
    if (password != confirmPassword) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Las contraseñas deben coincidir",
                color = Color.Red,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun PasswordIsValid(password: String) {
    val passwordPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}\$")
    if (!passwordPattern.matches(password)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "La contraseña debe tener al menos 6 dígitos, un número y una letra",
                color = Color.Red,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

fun validatePassword(password: String, confirmPassword: String): Boolean {
    val passwordPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}\$")
    return if ((password == confirmPassword) && (passwordPattern.matches(password))) true else false
}

fun validateFields(
    email: String,
    sex: String,
    name: String,
    last: String,
    birthday: String,
): Boolean {
    return !(email.isEmpty() || sex.isEmpty() || name.isEmpty() || last.isEmpty() || birthday.isEmpty())
}