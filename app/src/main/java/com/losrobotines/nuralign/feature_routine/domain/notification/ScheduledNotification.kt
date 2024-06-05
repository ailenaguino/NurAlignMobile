package com.losrobotines.nuralign.feature_routine.domain.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.losrobotines.nuralign.R

class ScheduledNotification : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION_TITLE = "notification_title"
        const val NOTIFICATION_CONTENT = "notification_content"
        const val NOTIFICATION_DESTINATION = "notification_destination"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val title = intent?.getStringExtra(NOTIFICATION_TITLE) ?: "Default Title"
            val content = intent?.getStringExtra(NOTIFICATION_CONTENT) ?: "Default Content"
            val destination = intent?.getStringExtra(NOTIFICATION_DESTINATION) ?: "DefaultDestination"
            val notificationId = intent?.getIntExtra(NOTIFICATION_ID, 0) ?: 0

            NotificationHelper.showNotification(it, title, content, destination, notificationId)
        }
    }
}

