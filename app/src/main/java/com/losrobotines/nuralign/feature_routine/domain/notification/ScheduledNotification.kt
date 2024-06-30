package com.losrobotines.nuralign.feature_routine.domain.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.Calendar

class ScheduledNotification : BroadcastReceiver() {

    @SuppressLint("ServiceCast", "ScheduleExactAlarm")
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(NOTIFICATION_TITLE) ?: "No title"
        val content = intent.getStringExtra(NOTIFICATION_CONTENT) ?: "No content"
        val destination = intent.getStringExtra(NOTIFICATION_DESTINATION) ?: "HomeScreen"
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        val repeat = intent.getBooleanExtra(NOTIFICATION_REPEAT, false)

        NotificationHelper.showNotification(context, title, content, destination, notificationId)

        if (repeat) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                add(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, get(Calendar.HOUR_OF_DAY))
                set(Calendar.MINUTE, get(Calendar.MINUTE))
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val newIntent = Intent(context, ScheduledNotification::class.java).apply {
                putExtra(NOTIFICATION_TITLE, title)
                putExtra(NOTIFICATION_CONTENT, content)
                putExtra(NOTIFICATION_DESTINATION, destination)
                putExtra(NOTIFICATION_ID, notificationId)
                putExtra(NOTIFICATION_REPEAT, true)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                newIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    companion object {
        const val NOTIFICATION_TITLE = "notification_title"
        const val NOTIFICATION_CONTENT = "notification_content"
        const val NOTIFICATION_DESTINATION = "notification_destination"
        const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION_REPEAT = "notification_repeat"
    }
}