package com.losrobotines.nuralign.feature_routine.domain.notification

import android.provider.Settings
import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import kotlinx.coroutines.runBlocking

class PermissionManager(private val activity: ComponentActivity) {


    private val permissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private val exactAlarmLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    suspend fun requestPermissions() {
        runBlocking {
            requestNotificationPermission()

            requestExactAlarmPermission()
        }
    }

    private suspend fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private suspend fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                exactAlarmLauncher.launch(intent)
            }
        }
    }
}

