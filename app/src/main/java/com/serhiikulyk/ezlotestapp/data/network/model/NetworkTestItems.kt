package com.serhiikulyk.ezlotestapp.data.network.model

import com.google.gson.annotations.SerializedName


data class NetworkTestItems (

  @SerializedName("Devices" ) var Devices : ArrayList<NetworkDevice> = arrayListOf()

)

