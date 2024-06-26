package com.losrobotines.nuralign.di

import com.google.firebase.auth.FirebaseAuth
import com.losrobotines.nuralign.BuildConfig
import com.losrobotines.nuralign.feature_achievements.data.AchievementRepositoryImpl
import com.losrobotines.nuralign.feature_achievements.data.database.AchievementDao
import com.losrobotines.nuralign.feature_achievements.data.database.CounterDao
import com.losrobotines.nuralign.feature_achievements.domain.AchievementRepository
import com.losrobotines.nuralign.feature_login.data.network.PatientApiService
import com.losrobotines.nuralign.feature_login.data.providers.AuthRepositoryImpl
import com.losrobotines.nuralign.feature_login.data.providers.PatientProviderImpl
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_login.domain.providers.PatientProvider
import com.losrobotines.nuralign.feature_medication.data.network.MedicationApiService
import com.losrobotines.nuralign.feature_medication.data.network.MedicationTrackerApiService
import com.losrobotines.nuralign.feature_medication.data.providers.MedicationProviderImpl
import com.losrobotines.nuralign.feature_medication.data.providers.MedicationTrackerProviderImpl
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationProvider
import com.losrobotines.nuralign.feature_medication.domain.providers.MedicationTrackerProvider
import com.losrobotines.nuralign.feature_medication.domain.usecases.tracker.SaveMedicationTrackerInfoUseCase
import com.losrobotines.nuralign.feature_medication.domain.usecases.tracker.UpdateMedicationTrackerInfoUseCase
import com.losrobotines.nuralign.feature_mood_tracker.data.MoodTrackerProviderImpl
import com.losrobotines.nuralign.feature_mood_tracker.data.network.MoodTrackerApiService
import com.losrobotines.nuralign.feature_mood_tracker.domain.MoodTrackerProvider
import com.losrobotines.nuralign.feature_weekly_summary.data.WeeklySummaryApiService
import com.losrobotines.nuralign.feature_weekly_summary.data.WeeklySummaryProviderImpl
import com.losrobotines.nuralign.feature_weekly_summary.domain.WeeklySummaryProvider
import com.losrobotines.nuralign.feature_routine.data.RoutineProviderImpl
import com.losrobotines.nuralign.feature_routine.data.database.RoutineDao
import com.losrobotines.nuralign.feature_routine.domain.RoutineProvider
import com.losrobotines.nuralign.feature_routine.domain.notification.Notification
import com.losrobotines.nuralign.feature_sleep.data.SleepTrackerProviderImpl
import com.losrobotines.nuralign.feature_sleep.data.network.SleepApiService
import com.losrobotines.nuralign.feature_sleep.domain.SleepTrackerProvider
import com.losrobotines.nuralign.feature_therapy.data.network.TherapistApiService
import com.losrobotines.nuralign.feature_therapy.data.network.TherapySessionApiService
import com.losrobotines.nuralign.feature_therapy.data.providers.TherapistProviderImpl
import com.losrobotines.nuralign.feature_therapy.data.providers.TherapySessionProviderImpl
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapistProvider
import com.losrobotines.nuralign.feature_therapy.domain.providers.TherapySessionProvider
import com.losrobotines.nuralign.gemini.GeminiContentGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_URL)
            .build()
    }

    //SIGNUP
    @Provides
    fun providePatientApiService(retrofit: Retrofit): PatientApiService {
        return retrofit.create(PatientApiService::class.java)
    }

    @Provides
    fun providePatientProvider(patientApiService: PatientApiService): PatientProvider {
        return PatientProviderImpl(patientApiService)
    }

    //SLEEP TRACKER
    @Provides
    fun provideSleepApiService(retrofit: Retrofit): SleepApiService {
        return retrofit.create(SleepApiService::class.java)
    }

    @Provides
    fun provideSleepRepository(sleepApiService: SleepApiService): SleepTrackerProvider {
        return SleepTrackerProviderImpl(sleepApiService)
    }

    @Provides
    fun provideMoodTrackerApiService(retrofit: Retrofit): MoodTrackerApiService {
        return retrofit.create(MoodTrackerApiService::class.java)
    }

    @Provides
    fun provideMoodTrackerRepository(moodTrackerApiService: MoodTrackerApiService): MoodTrackerProvider {
        return MoodTrackerProviderImpl(moodTrackerApiService)
    }

    @Provides
    fun provideAchievementRespository(
        achievementDao: AchievementDao,
        counterDao: CounterDao
    ): AchievementRepository {
        return AchievementRepositoryImpl(achievementDao, counterDao)
    }

    @Provides
    @Singleton
    fun provideGeminiContentGenerator(): GeminiContentGenerator {
        return GeminiContentGenerator()
    }

    @Provides
    fun provideNotification(): Notification {
        return Notification()
    }

    @Provides
    fun provideMedicationApiService(retrofit: Retrofit): MedicationApiService {
        return retrofit.create(MedicationApiService::class.java)
    }

    @Provides
    fun provideMedicationProvider(medicationApiService: MedicationApiService): MedicationProvider {
        return MedicationProviderImpl(medicationApiService)
    }

    @Provides
    fun provideMedicationTrackerApiService(retrofit: Retrofit): MedicationTrackerApiService {
        return retrofit.create(MedicationTrackerApiService::class.java)
    }

    @Provides
    fun provideMedicationTrackerProvider(medicationTrackerApiService: MedicationTrackerApiService): MedicationTrackerProvider {
        return MedicationTrackerProviderImpl(medicationTrackerApiService)
    }

    @Provides
    fun provideSaveMedicationTrackerInfoUseCase(medicationTrackerProvider: MedicationTrackerProvider): SaveMedicationTrackerInfoUseCase {
        return SaveMedicationTrackerInfoUseCase(medicationTrackerProvider)
    }

    @Provides
    fun provideUpdateMedicationTrackerInfoUseCase(medicationTrackerProvider: MedicationTrackerProvider): UpdateMedicationTrackerInfoUseCase {
        return UpdateMedicationTrackerInfoUseCase(medicationTrackerProvider)
    }

    @Provides
    fun provideTherapistApiService(retrofit: Retrofit): TherapistApiService {
        return retrofit.create(TherapistApiService::class.java)
    }

    @Provides
    fun provideTherapistProvider(therapistApiService: TherapistApiService): TherapistProvider {
        return TherapistProviderImpl(therapistApiService)
    }

    @Provides
    fun provideRoutineProvider(dao: RoutineDao): RoutineProvider {
        return RoutineProviderImpl(dao)

    }

    @Provides
    fun provideTherapySessionApiService(retrofit: Retrofit): TherapySessionApiService {
        return retrofit.create(TherapySessionApiService::class.java)
    }

    @Provides
    fun provideTherapySessionProvider(therapySessionApiService: TherapySessionApiService): TherapySessionProvider {
        return TherapySessionProviderImpl(therapySessionApiService)
    }

    @Provides
    @Singleton
    fun provideWeeklySummary(retrofit: Retrofit): WeeklySummaryApiService {
        return retrofit.create(WeeklySummaryApiService::class.java)
    }

    @Provides
    fun provideWeeklySummaryProvider(weeklySummaryApiService: WeeklySummaryApiService): WeeklySummaryProvider {
        return WeeklySummaryProviderImpl(weeklySummaryApiService)
    }

}