package com.serhiikulyk.ezlotestapp.data.repository

import com.serhiikulyk.ezlotestapp.data.local.DeviceDao
import com.serhiikulyk.ezlotestapp.data.network.ApiService
import com.serhiikulyk.ezlotestapp.ext.generateDeviceTitle
import com.serhiikulyk.ezlotestapp.ext.toEntity
import javax.inject.Inject

class DevicesRepository @Inject constructor(
    private val apiService: ApiService,
    private val deviceDao: DeviceDao,
) {


    // TASK 7: The fetched list should be stored by using a database.  And the button should provide a way to fetch the data from the API and reset the items on the db and on the list.
    suspend fun fetchAndSaveTestItems() {
        val networkTestItems = apiService.getTestItems()
        val deviceEntities = networkTestItems.Devices
            .sortedBy { it.PKDevice }
            .mapIndexed { index, networkDevice -> networkDevice.toEntity(title = generateDeviceTitle(index)) }
        deviceDao.dropAndCache(deviceEntities)
    }

    fun getDevicesStream() = deviceDao.getStream()

    suspend fun getDevice(pkDevice: Int) = deviceDao.get(pkDevice)

    suspend fun updateTitle(pkDevice: Int, newTitle: String) {
        deviceDao.updateTitle(pkDevice, newTitle)
    }

    suspend fun deleteDevice(pkDevice: Int) {
        deviceDao.delete(pkDevice)
    }

}

