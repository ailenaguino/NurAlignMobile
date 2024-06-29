package com.losrobotines.nuralign.feature_achievements.domain.usecases

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_achievements.domain.models.Counter
import com.losrobotines.nuralign.feature_achievements.presentation.screens.Channel
import com.losrobotines.nuralign.navigation.MainActivity
import com.losrobotines.nuralign.ui.preferences.PreferencesManager
import javax.inject.Inject

class TrackerIsSavedUseCase @Inject constructor(
    private val getCounterUseCase: GetCounterUseCase,
    private val startCounterUseCase: StartCounterUseCase,
    private val addOneToCounterUseCase: AddOneToCounterUseCase,
    private val formatCorrectAchievementAndMessageUseCase: FormatCorrectAchievementAndMessageUseCase
) {

    suspend operator fun invoke(context: Context, tracker:String){
        var counter = getCounterUseCase(tracker)
        if (counter == null) {
            startCounterUseCase(tracker)
        } else {
            addOneToCounterUseCase(tracker)
        }
        counter = getCounterUseCase(tracker)
        val message =
            formatCorrectAchievementAndMessageUseCase(
                Counter(
                    tracker,
                    counter ?: 1
                )
            )
        if (message.isNotEmpty()) {
            createSimpleNotification(context, message)
        }
    }

    fun createSimpleNotification(context: Context, message: String) {
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
            .setContentTitle("¡Logro Conseguido!")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(PreferencesManager(context).getBoolean("Notifications", true)) {
            notificationManager.notify(1, notification)
        }
    }
}