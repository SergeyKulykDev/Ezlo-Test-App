package com.serhiikulyk.ezlotestapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serhiikulyk.ezlotestapp.data.local.model.DeviceEntity

@Database(entities = [DeviceEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao

}
