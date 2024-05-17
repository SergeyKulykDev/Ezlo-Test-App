package com.serhiikulyk.ezlotestapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.serhiikulyk.ezlotestapp.data.local.model.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Query("SELECT * FROM devices WHERE pk_device = :pkDevice")
    suspend fun get(pkDevice: Int): DeviceEntity?

    @Query("SELECT * FROM devices")
    fun getStream(): Flow<List<DeviceEntity>>

    @Insert
    suspend fun insertAll(deviceEntities: List<DeviceEntity>)

    @Query("UPDATE devices SET title = :newTitle WHERE pk_device == :pkDevice")
    suspend fun updateTitle(pkDevice: Int, newTitle: String)


    @Query("DELETE FROM devices WHERE pk_device = :pkDevice")
    suspend fun delete(pkDevice: Int)

    @Query("DELETE FROM devices")
    suspend fun deleteAll()

    @Transaction
    suspend fun dropAndCache(deviceEntities: List<DeviceEntity>) {
        deleteAll()
        insertAll(deviceEntities)
    }

}