package com.serhiikulyk.ezlotestapp.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkDevice (

  @SerializedName("PK_Device"         ) var PKDevice          : Int?    = null,
  @SerializedName("MacAddress"        ) var MacAddress        : String? = null,
  @SerializedName("PK_DeviceType"     ) var PKDeviceType      : Int?    = null,
  @SerializedName("PK_DeviceSubType"  ) var PKDeviceSubType   : Int?    = null,
  @SerializedName("Server_Device"     ) var ServerDevice      : String? = null,
  @SerializedName("Server_Event"      ) var ServerEvent       : String? = null,
  @SerializedName("PK_Account"        ) var PKAccount         : Int?    = null,
  @SerializedName("Firmware"          ) var Firmware          : String? = null,
  @SerializedName("Server_Account"    ) var ServerAccount     : String? = null,
  @SerializedName("InternalIP"        ) var InternalIP        : String? = null,
  @SerializedName("LastAliveReported" ) var LastAliveReported : String? = null,
  @SerializedName("Platform" )          var Platform : String? = null

)