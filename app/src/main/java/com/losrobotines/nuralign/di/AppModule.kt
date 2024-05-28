package com.losrobotines.nuralign.di

import com.google.firebase.auth.FirebaseAuth
import com.losrobotines.nuralign.BuildConfig
import com.losrobotines.nuralign.feature_login.data.network.PatientApiService
import com.losrobotines.nuralign.feature_login.data.providers.AuthRepositoryImpl
import com.losrobotines.nuralign.feature_login.data.providers.PatientProviderImpl
import com.losrobotines.nuralign.feature_login.domain.providers.AuthRepository
import com.losrobotines.nuralign.feature_login.domain.providers.PatientProvider
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.MoodTrackerProviderImpl
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.data.network.MoodTrackerApiService
import com.losrobotines.nuralign.feature_mood_tracker.presentation.screens.domain.MoodTrackerProvider
import com.losrobotines.nuralign.feature_sleep.data.SleepRepositoryImpl
import com.losrobotines.nuralign.feature_sleep.data.network.SleepApiService
import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
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
    fun provideSleepRepository(sleepApiService: SleepApiService): SleepRepository {
        return SleepRepositoryImpl(sleepApiService)
    }

    @Provides
    fun provideMoodTrackerApiService(retrofit: Retrofit): MoodTrackerApiService {
        return retrofit.create(MoodTrackerApiService::class.java)
    }

    @Provides
    fun provideMoodTrackerRepository(moodTrackerApiService: MoodTrackerApiService): MoodTrackerProvider {
        return MoodTrackerProviderImpl(moodTrackerApiService)
    }


}