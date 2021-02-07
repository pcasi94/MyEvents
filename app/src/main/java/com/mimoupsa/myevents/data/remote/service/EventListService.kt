package com.mimoupsa.myevents.data.remote.service

import com.mimoupsa.myevents.BuildConfig
import com.mimoupsa.myevents.data.remote.model.EventData
import com.mimoupsa.myevents.data.remote.model.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventListService {

    @GET("events")
    fun getEvents(
            @Query("countryCode")string: String = "es",
            @Query("apikey")apiKey: String = BuildConfig.API_KEY,
            @Query("keyword")keyword: String?
    ): Call<ResponseData>

    @GET("events/{eventID}")
    fun getEventDetail(
            @Path("eventID") eventId: String,
            @Query("countryCode")string: String = "es",
            @Query("apikey")apiKey: String = BuildConfig.API_KEY
    ): Call<EventData>

    @GET("events")
    fun getEventsByLocation(
        @Query("page")page:Int,
        @Query("radius")radius:String = "12",
        @Query("unit")unit:String = "km",
        @Query("geoPoint")geoPoint:String,
        @Query("apikey")apiKey: String = BuildConfig.API_KEY
    ): Call<ResponseData>

}