package com.losrobotines.nuralign.feature_login.presentation.screens.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.losrobotines.nuralign.feature_login.presentation.screens.login.LoginScreen
import com.losrobotines.nuralign.feature_login.presentation.screens.login.LoginViewModel
import com.losrobotines.nuralign.ui.theme.NurAlignTheme
import com.losrobotines.nuralign.ui.theme.secondaryColor
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Home : ComponentActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurAlignTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(text = "Hola ${viewModel.curretUser?.displayName}")
                    Button(
                        onClick = {
                                  viewModel.logout()
                            if (viewModel.curretUser==null){
                                val intent = Intent(this, LoginScreen::class.java)
                                startActivity(intent)
                                finish()
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
                        Text("LogOut")
                    }
                }
            }
        }
    }
}