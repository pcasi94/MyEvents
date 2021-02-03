package com.mimoupsa.myevents.data.remote.service

import com.mimoupsa.myevents.BuildConfig
import com.mimoupsa.myevents.data.remote.model.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EventListService {

    @GET("events")
    fun getEvents(
            @Query("countryCode")string: String = "es",
            @Query("apikey")apiKey: String = BuildConfig.API_KEY
    ): Call<ResponseData>

}