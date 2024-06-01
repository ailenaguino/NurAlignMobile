package com.losrobotines.nuralign.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

class Notification {

    @SuppressLint("ScheduleExactAlarm")
    fun notificacionProgramada(context: Context) {
        val intent = Intent(context, ScheduledNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ScheduledNotification.NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().timeInMillis + 1000,
            pendingIntent
        )
    }
}

