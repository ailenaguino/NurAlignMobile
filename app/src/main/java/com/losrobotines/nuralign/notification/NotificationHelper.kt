package com.losrobotines.nuralign.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.losrobotines.nuralign.R

object NotificationHelper {

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Robotin Notifications"
            val descriptionText = "Channel for Robotin notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Robotin", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(context: Context) {
        val notification = NotificationCompat.Builder(context, "Robotin")
            .setSmallIcon(R.drawable.robotin)
            .setContentTitle("Robotin")
            .setContentText("Buenos dias como dormiste anoche")
            .setStyle(NotificationCompat.BigTextStyle().bigText("YA ES TARDE Clickame para completar como dormiste"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ScheduledNotification.NOTIFICATION_ID, notification)
    }
}