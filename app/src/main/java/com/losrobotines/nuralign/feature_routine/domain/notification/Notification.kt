package com.losrobotines.nuralign.feature_routine.domain.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.losrobotines.nuralign.R
import java.time.LocalTime
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
class Notification @Inject constructor(){

    @SuppressLint("ScheduleExactAlarm", "NewApi", "LaunchActivityFromNotification")
    fun notificacionProgramada(
        context: Context,
        selectedTime: LocalTime,
        title: String,
        content: String,
        destination: String,
        channelId: String,
        notificationId: Int
    ) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, selectedTime.hour)
            set(Calendar.MINUTE, selectedTime.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // Si la hora seleccionada ya pasó para hoy, programa para el día siguiente
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val intent = Intent(context, ScheduledNotification::class.java).apply {
            putExtra(ScheduledNotification.NOTIFICATION_TITLE, title)
            putExtra(ScheduledNotification.NOTIFICATION_CONTENT, content)
            putExtra(ScheduledNotification.NOTIFICATION_DESTINATION, destination)
            putExtra(ScheduledNotification.NOTIFICATION_ID, notificationId) // Pasar el ID único
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId, // Usar el ID único para la notificación
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}
