package com.losrobotines.nuralign.feature_routine.domain.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.LocalTime
import java.util.Calendar
import javax.inject.Inject
class Notification @Inject constructor() {
    @SuppressLint("ScheduleExactAlarm", "NewApi", "LaunchActivityFromNotification")
    fun scheduledNotification(
        context: Context,
        selectedTime: LocalTime,
        title: String,
        content: String,
        destination: String,
        notificationId: Int,
        selectedDays: List<String>? = null
    ) {
        NotificationHelper.createNotificationChannel(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ScheduledNotification::class.java).apply {
            putExtra(ScheduledNotification.NOTIFICATION_TITLE, title)
            putExtra(ScheduledNotification.NOTIFICATION_CONTENT, content)
            putExtra(ScheduledNotification.NOTIFICATION_DESTINATION, destination)
            putExtra(ScheduledNotification.NOTIFICATION_ID, notificationId)
        }

        if (selectedDays.isNullOrEmpty()) {
            // Para notificación diaria
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, selectedTime.hour)
                set(Calendar.MINUTE, selectedTime.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                if (before(Calendar.getInstance())) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.cancel(pendingIntent)

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        } else {
            val daysOfWeek = mapOf(
                "Lu" to Calendar.MONDAY,
                "Ma" to Calendar.TUESDAY,
                "Mi" to Calendar.WEDNESDAY,
                "Ju" to Calendar.THURSDAY,
                "Vi" to Calendar.FRIDAY,
                "Sa" to Calendar.SATURDAY,
                "Do" to Calendar.SUNDAY
            )

            for (day in selectedDays) {
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, selectedTime.hour)
                    set(Calendar.MINUTE, selectedTime.minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    daysOfWeek[day]?.let { set(Calendar.DAY_OF_WEEK, it) }

                    if (before(Calendar.getInstance())) {
                        add(Calendar.WEEK_OF_YEAR, 1)
                    }
                }

                val daySpecificIntent = Intent(intent).apply {
                    putExtra(ScheduledNotification.NOTIFICATION_ID, notificationId + daysOfWeek[day]!!)
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationId + daysOfWeek[day]!!,
                    daySpecificIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )

                alarmManager.cancel(pendingIntent)

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY * 7,  // Repite cada semana en el mismo día
                    pendingIntent
                )
            }
        }
    }
}