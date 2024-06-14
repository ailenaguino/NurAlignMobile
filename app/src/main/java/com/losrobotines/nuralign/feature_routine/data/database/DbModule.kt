package com.losrobotines.nuralign.feature_routine.data.database

import android.content.Context
import androidx.room.Room
import com.losrobotines.nuralign.feature_routine.data.RoutineDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RoutineDatabase::class.java, "routine_database")
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(db: RoutineDatabase) = db.routineDao()

}