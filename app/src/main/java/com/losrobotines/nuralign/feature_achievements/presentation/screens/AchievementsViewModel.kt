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
import com.losrobotines.nuralign.feature_achievements.domain.models.Achievement
import com.losrobotines.nuralign.feature_achievements.domain.models.Counter
import com.losrobotines.nuralign.feature_achievements.domain.usecases.AddOneToCounterUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.GetCounterUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.RestartCountersUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.FormatCorrectAchievementAndMessageUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.GetUserAchievementsUseCase
import com.losrobotines.nuralign.feature_achievements.domain.usecases.StartCounterUseCase
import com.losrobotines.nuralign.navigation.MainActivity
import com.losrobotines.nuralign.ui.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val getCounterUseCase: GetCounterUseCase,
    private val startCounterUseCase: StartCounterUseCase,
    private val addOneToCounterUseCase: AddOneToCounterUseCase,
    private val formatCorrectAchievementAndMessageUseCase: FormatCorrectAchievementAndMessageUseCase,
    private val restartCountersUseCase: RestartCountersUseCase,
    private val getUserAchievementsUseCase: GetUserAchievementsUseCase,
) : ViewModel() {

    private val _achievementList = MutableLiveData<List<Achievement>>()
    var achievementList = _achievementList

    object TrackerConstants {
        const val MOOD_TRACKER = "mood"
        const val SLEEP_TRACKER = "sleep"
        const val THERAPY_TRACKER = "therapy"
        const val MEDICATION_TRACKER = "medication"
    }

    fun getAchievements() {
        viewModelScope.launch {
            _achievementList.value = getUserAchievementsUseCase()
        }
    }

    fun trackerIsSaved(context: Context, tracker:String) {
        viewModelScope.launch {
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
    }

    fun restartCounters() {
        viewModelScope.launch { restartCountersUseCase() }
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