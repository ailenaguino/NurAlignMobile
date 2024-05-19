package com.losrobotines.nuralign.feature_settings.presentation.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.losrobotines.nuralign.feature_login.presentation.screens.login.LoginViewModel
import com.losrobotines.nuralign.navigation.Routes
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.mainColor
import com.losrobotines.nuralign.ui.theme.secondaryColor


@SuppressLint("PrivateResource", "NotConstructor")
@Composable
fun SettingsScreenComponent(navigationController: NavHostController, viewModel: LoginViewModel) {
    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        item {
            SharedComponents().HalfCircleTop("Ajustes")
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        "Notificaciones", style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = FontFamily.Default,
                            color = secondaryColor
                        )
                    )
                    Divider(
                        color = secondaryColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .weight(0.8f)
                    ) {
                        Text(
                            "Mostrar notificaciones generales",
                            color = secondaryColor,
                            fontSize = 16.sp,
                        )
                    }
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.weight(0.2f)
                    ) {
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
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    "Datos Personales", style = TextStyle(
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Default,
                        color = secondaryColor
                    )
                )
                Divider(
                    color = secondaryColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Editar datos personales",
                    color = secondaryColor,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        navigationController.navigate(Routes.PersonalInformationScreen.route)
                    }
                )
                Spacer(modifier = Modifier.height(200.dp))

                Text(
                    text = "Cerrar sesión",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable {
                        viewModel.logout()
                        navigationController.navigate(Routes.LoginScreen.route)
                    }
                )
            }
        }
    }
}


