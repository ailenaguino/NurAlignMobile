package com.losrobotines.nuralign.feature_achievements.presentation.screens

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.losrobotines.nuralign.R
import com.losrobotines.nuralign.feature_achievements.domain.models.Counter
import com.losrobotines.nuralign.feature_achievements.domain.usecases.AddOneToCounterUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.GetCounterUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.RestartCountersUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.SendNotificationIfNeededUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.StartCounterUseCase
import com.losrobotines.nuralign.navigation.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val getCounterUseCase: GetCounterUseCase,
    private val startCounterUseCase: StartCounterUseCase,
    private val addOneToCounterUseCase: AddOneToCounterUseCase,
    private val sendNotificationIfNeededUseCase: SendNotificationIfNeededUseCase,
    private val restartCountersUseCase: RestartCountersUseCase
) : ViewModel() {

    private val _sendNotification = MutableLiveData(false)
    var sendNotification = _sendNotification

    private val _message = MutableLiveData("")
    var message = _message

    object TrackerConstants {
        const val MOOD_TRACKER = "mood"
        const val SLEEP_TRACKER = "sleep"
        const val THERAPY_TRACKER = "therapy"
        const val MEDICATION_TRACKER = "medication"
    }

    fun moodTrackerIsSaved(context: Context) {
        viewModelScope.launch {
            var counter = getCounterUseCase(TrackerConstants.MOOD_TRACKER)
            if (counter == null) {
                startCounterUseCase(TrackerConstants.MOOD_TRACKER)
            } else {
                addOneToCounterUseCase(TrackerConstants.MOOD_TRACKER)
            }
            counter = getCounterUseCase(TrackerConstants.MOOD_TRACKER)
            val message =
                sendNotificationIfNeededUseCase(Counter(TrackerConstants.MOOD_TRACKER, counter?:1))
            if (message.isNotEmpty()) {
                createSimpleNotification(context, message)
        }
    }
}

fun sleepTrackerIsSaved() {
    viewModelScope.launch {
        val counter = getCounterUseCase(TrackerConstants.SLEEP_TRACKER)
        if (counter == null) {
            startCounterUseCase(TrackerConstants.SLEEP_TRACKER)
        } else {
            addOneToCounterUseCase(TrackerConstants.SLEEP_TRACKER)
            sendNotificationIfNeededUseCase(Counter(TrackerConstants.SLEEP_TRACKER, counter))
        }
    }
}

fun medicationTrackerIsSaved() {
    viewModelScope.launch {
        val counter = getCounterUseCase(TrackerConstants.MEDICATION_TRACKER)
        if (counter == null) {
            startCounterUseCase(TrackerConstants.MEDICATION_TRACKER)
        } else {
            addOneToCounterUseCase(TrackerConstants.MEDICATION_TRACKER)
            sendNotificationIfNeededUseCase(Counter(TrackerConstants.MEDICATION_TRACKER, counter))
        }
    }
}

fun therapyTrackerIsSaved() {
    viewModelScope.launch {
        val counter = getCounterUseCase(TrackerConstants.THERAPY_TRACKER)
        if (counter == null) {
            startCounterUseCase(TrackerConstants.THERAPY_TRACKER)
        } else {
            addOneToCounterUseCase(TrackerConstants.THERAPY_TRACKER)
            sendNotificationIfNeededUseCase(Counter(TrackerConstants.THERAPY_TRACKER, counter))
        }
    }
}
    fun setSendNotification(value: Boolean){
        _sendNotification.value = value
    }

    fun restartCounters(){
        viewModelScope.launch { restartCountersUseCase() }
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
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)

    }
}