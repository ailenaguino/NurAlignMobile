package com.losrobotines.nuralign.feature_achievements.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_achievements.presentation.utils.AchievementsData
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.green
import com.losrobotines.nuralign.ui.theme.pink
import com.losrobotines.nuralign.ui.theme.purple
import com.losrobotines.nuralign.ui.theme.secondaryColor
import com.losrobotines.nuralign.ui.theme.turquoise


@SuppressLint("NotConstructor")
@Composable
fun AchievementsScreenComponent(navController: NavController) {
    val achievementsList = listOf(
        AchievementsData.animoBronce,
        AchievementsData.animoPlata,
        AchievementsData.animoOro,
        AchievementsData.medicacionBronce,
        AchievementsData.medicacionPlata,
        AchievementsData.medicacionOro,
        AchievementsData.suenioBronce,
        AchievementsData.suenioPlata,
        AchievementsData.suenioOro,
        AchievementsData.terapiaBronce,
        AchievementsData.terapiaPlata,
        AchievementsData.terapiaOro
    )
    SharedComponents().HalfCircleTop("Logros")
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 170.dp)
    ) {
        items(achievementsList.size) { item ->
            Achievement(achievementsList[item])
        }
        item{
            ButtonToTryAchievements(onClick = { /*TODO*/ }, color = turquoise)
        }
        item{
            ButtonToTryAchievements(onClick = { /*TODO*/ }, color = green)
        }
        item{
            ButtonToTryAchievements(onClick = { /*TODO*/ }, color = purple)
        }
        item{
            ButtonToTryAchievements(onClick = { /*TODO*/ }, color = pink)
        }
    }
}

@Composable
private fun Achievement(achievement: AchievementsData) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.CenterStart)
        {
            Image(
                painterResource(id = achievement.image),
                contentDescription = "Achievement ${achievement.name}",
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }),
                modifier = Modifier
                    .size(90.dp)
                    .clickable {
                        openAlertDialog = true
                    }
            )
            if (openAlertDialog) {
                AchievementAlertDialog(
                    onDismissRequest = { openAlertDialog = false },
                    dialogTitle = achievement.name,
                    dialogText = achievement.descr,
                    image = achievement.image
                )
            }
        }
    }
}

@Composable
private fun AchievementAlertDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    image: Int,
) {
    AlertDialog(
        icon = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(start = 25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = image),
                        contentDescription = "Fondo",
                        modifier = Modifier
                            .size(85.dp)
                    )

                }
                Box(
                    modifier = Modifier.weight(0.1f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Icon(
                        Icons.Default.Close,
                        "close",
                        modifier = Modifier.clickable { onDismissRequest() })
                }
            }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {}
    )
}

@Composable
fun ButtonToTryAchievements(onClick:()->Unit, color: Color){
    Button(onClick = { onClick },
        colors = ButtonDefaults.buttonColors(containerColor = color)){}
}

