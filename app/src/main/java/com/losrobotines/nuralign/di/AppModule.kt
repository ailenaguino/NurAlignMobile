package com.losrobotines.nuralign.di

import com.google.firebase.auth.FirebaseAuth
import com.losrobotines.nuralign.feature_login.data.AuthRepositoryImpl
import com.losrobotines.nuralign.feature_login.domain.AuthRepository
import com.losrobotines.nuralign.feature_sleep.data.SleepRepositoryImpl
import com.losrobotines.nuralign.feature_sleep.domain.SleepRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideSleepRepository(): SleepRepository {
        return SleepRepositoryImpl()
    }
}