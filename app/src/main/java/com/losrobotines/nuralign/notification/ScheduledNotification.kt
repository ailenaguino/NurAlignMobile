package com.losrobotines.nuralign.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.losrobotines.nuralign.R

class ScheduledNotification: BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        newNotification(context)
    }

    private fun newNotification(context: Context?) {
        if (context == null) return

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the NotificationChannel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Robotin",
                "Robotin Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for Robotin notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "Robotin")
            .setSmallIcon(R.drawable.robotin_bebe)
            .setContentTitle("Robotin")
            .setContentText("Buenos dias como dormiste anoche")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Ya Clieakeme para completar como dormiste")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}

