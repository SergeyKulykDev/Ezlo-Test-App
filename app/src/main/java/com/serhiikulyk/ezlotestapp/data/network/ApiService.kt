package com.serhiikulyk.ezlotestapp.data.network

import com.serhiikulyk.ezlotestapp.data.network.model.NetworkTestItems
import retrofit2.http.GET

interface ApiService {

    @GET("test_android/items.test")
    suspend fun getTestItems(): NetworkTestItems

}