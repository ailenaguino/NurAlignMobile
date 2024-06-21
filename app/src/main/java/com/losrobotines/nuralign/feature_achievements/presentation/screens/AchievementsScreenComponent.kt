package com.losrobotines.nuralign.feature_achievements.presentation.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_achievements.presentation.utils.AchievementsData
import com.losrobotines.nuralign.navigation.MainActivity
import com.losrobotines.nuralign.ui.shared.SharedComponents
import com.losrobotines.nuralign.ui.theme.green
import com.losrobotines.nuralign.ui.theme.pink
import com.losrobotines.nuralign.ui.theme.purple
import com.losrobotines.nuralign.ui.theme.turquoise

object Channel {
    const val MY_CHANNEL_ID = "Robotin"
}

@SuppressLint("NotConstructor")
@Composable
fun AchievementsScreenComponent(
    navController: NavController,
    achievementViewModel: AchievementsViewModel,
) {
    val context = LocalContext.current.applicationContext
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
        item {
            ButtonToTryAchievements(
                onClick = { achievementViewModel.moodTrackerIsSaved(context) },
                color = turquoise,
                achievementViewModel
            )
        }
        item {
            ButtonToTryAchievements(
                onClick = { achievementViewModel.medicationTrackerIsSaved() },
                color = green,
                achievementViewModel
            )
        }
        item {
            ButtonToTryAchievements(
                onClick = { achievementViewModel.sleepTrackerIsSaved() },
                color = purple,
                achievementViewModel
            )
        }
        item {
            ButtonToTryAchievements(
                onClick = { achievementViewModel.therapyTrackerIsSaved() },
                color = pink,
                achievementViewModel
            )
        }

        item {
            ButtonToTryAchievements(
                onClick = { achievementViewModel.restartCounters() },
                color = Color.Gray,
                achievementViewModel
            )
        }
    }
    /*
    when(achievementViewModel.sendNotification.value){
        true -> {createSimpleNotification(context, achievementViewModel.message.value?: "")
                achievementViewModel.setSendNotification(false)}
        else -> ""
    }
    if(achievementViewModel.sendNotification.value!!){
        createSimpleNotification(context, achievementViewModel.message.value?: "")
        achievementViewModel.setSendNotification(false)
    }*/
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
fun ButtonToTryAchievements(onClick: () -> Unit, color: Color, viewModel: AchievementsViewModel) {

    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {}
}

fun createSimpleNotification(context: Context, message: String){
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, Channel.MY_CHANNEL_ID)
        .setSmallIcon(R.drawable.robotin_bebe)
        .setContentTitle("Logro conseguido")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(1, notification)

}

