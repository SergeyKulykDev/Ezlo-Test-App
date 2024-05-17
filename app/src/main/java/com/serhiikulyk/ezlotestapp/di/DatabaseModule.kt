package com.serhiikulyk.ezlotestapp.di

import android.content.Context
import androidx.room.Room
import com.serhiikulyk.ezlotestapp.const.DB_NAME
import com.serhiikulyk.ezlotestapp.data.local.AppDatabase
import com.serhiikulyk.ezlotestapp.data.local.DeviceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideDeviceDao(database: AppDatabase): DeviceDao {
        return database.deviceDao()
    }
}