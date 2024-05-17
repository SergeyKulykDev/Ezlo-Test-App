package com.serhiikulyk.ezlotestapp.ext

import com.serhiikulyk.ezlotestapp.data.local.model.DeviceEntity
import com.serhiikulyk.ezlotestapp.data.network.model.NetworkDevice

fun NetworkDevice.toEntity(title: String) = DeviceEntity(
    pkDevice = PKDevice ?: 0,
    macAddress = MacAddress,
    pkDeviceType = PKDeviceType,
    pkDeviceSubType = PKDeviceSubType,
    serverDevice = ServerDevice,
    serverEvent = ServerEvent,
    pkAccount = PKAccount,
    firmware = Firmware,
    serverAccount = ServerAccount,
    internalIP = InternalIP,
    lastAliveReported = LastAliveReported,
    platform = Platform,
    title = title
)

// TASK 4: List Item titles should be generated
fun generateDeviceTitle(index: Int) = "Home Item ${index + 1}"
