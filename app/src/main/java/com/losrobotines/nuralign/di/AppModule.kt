package com.losrobotines.nuralign.di

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.losrobotines.nuralign.feature_login.data.AuthRepositoryImpl
import com.losrobotines.nuralign.feature_login.domain.AuthRepository
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
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://77.37.69.38:8081/api/")
            .build()
    }


    //SLEEP TRACKER
    @Provides
    fun provideSleepApiService(retrofit: Retrofit):SleepApiService{
        return retrofit.create(SleepApiService::class.java)
    }
    @Provides
    fun provideSleepRepository(sleepApiService: SleepApiService): SleepRepository {
        return SleepRepositoryImpl(sleepApiService)
    }
}