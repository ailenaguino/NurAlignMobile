package com.losrobotines.nuralign.feature_achievements.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.NurAlignTheme

class AchievementsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurAlignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AchievementsScreen()
                }
            }
        }
    }

    @SuppressLint("NotConstructor")
    @Preview
    @Composable
    private fun AchievementsScreen() {
        val achievements = listOf(
            "Ánimo de bronce",
            "Ánimo de plata",
            "Ánimo de oro",
            "Acompañante a medida",
            "Rutinario",
            "Medicación de bronce",
            "Medicación de plata",
            "Medicación de oro",
            "Terapia de bronce",
            "Terapia de plata",
        )
        SharedComponents().HalfCircleTop("Logros")
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 170.dp)
        ) {
            items(achievements.size) { item ->
                Achievement(achievements[item])
            }
        }
    }

    @Composable
    private fun Achievement(achievementName: String) {
        var openAlertDialog by remember { mutableStateOf(false) }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.CenterStart)
            {
                Image(
                    painterResource(id = R.drawable.achievement_default),
                    contentDescription = "Achievement $achievementName",
                    modifier = Modifier
                        .size(90.dp)
                        .clickable {
                            openAlertDialog = true
                        }
                )
                if (openAlertDialog) {
                    AchievementAlertDialog(
                        onDismissRequest = { openAlertDialog = false },
                        onConfirmation = { openAlertDialog = false },
                        dialogTitle = achievementName,
                        dialogText = "Descripción del logro $achievementName",
                        icon = Icons.Default.Info
                    )
                }
            }
        }
    }

    @Composable
    private fun AchievementAlertDialog(
        onDismissRequest: () -> Unit,
        onConfirmation: () -> Unit,
        dialogTitle: String,
        dialogText: String,
        icon: ImageVector
    ) {
        AlertDialog(
            icon = {
                Icon(icon, contentDescription = "Example Icon")
            },
            title = {
                Text(text = dialogTitle)
            },
            text = {
                Text(text = dialogText)
            },
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}