package com.serhiikulyk.ezlotestapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey
    @ColumnInfo("pk_device")
    var pkDevice: Int = 0,

    @ColumnInfo("mac_address")
    var macAddress: String? = null,

    @ColumnInfo("pk_device_type")
    var pkDeviceType: Int? = null,

    @ColumnInfo("pk_device_subtype")
    var pkDeviceSubType: Int? = null,

    @ColumnInfo("server_device")
    var serverDevice: String? = null,

    @ColumnInfo("server_event")
    var serverEvent: String? = null,

    @ColumnInfo("pk_account")
    var pkAccount: Int? = null,

    @ColumnInfo("firmware")
    var firmware: String? = null,

    @ColumnInfo("server_account")
    var serverAccount: String? = null,

    @ColumnInfo("internal_ip")
    var internalIP: String? = null,

    @ColumnInfo("last_alive_reported")
    var lastAliveReported: String? = null,

    @ColumnInfo("platform")
    var platform: String? = null,

    @ColumnInfo("title")
    var title: String? = null

)

