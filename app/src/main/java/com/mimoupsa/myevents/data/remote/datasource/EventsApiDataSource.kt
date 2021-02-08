package com.mimoupsa.myevents.data.remote.datasource

import android.util.Log
import com.mimoupsa.myevents.BuildConfig
import com.mimoupsa.myevents.data.remote.RetrofitHelper
import com.mimoupsa.myevents.data.remote.ServiceCallback
import com.mimoupsa.myevents.data.remote.callback.CallbackEventDetail
import com.mimoupsa.myevents.data.remote.callback.CallbackEvents
import com.mimoupsa.myevents.data.remote.model.EventData
import com.mimoupsa.myevents.data.remote.model.ResponseData
import com.mimoupsa.myevents.data.remote.service.EventListService
import com.mimoupsa.myevents.domain.mappers.EventMapper
import com.mimoupsa.myevents.domain.mappers.GeoPointMapper
import com.mimoupsa.myevents.domain.model.EventList
import com.mimoupsa.myevents.domain.model.Location

class EventsApiDataSource {
    companion object {
        val INSTANCE: EventsApiDataSource = EventsApiDataSource()
    }

    private val service: EventListService by lazy {
        RetrofitHelper.getRetrofit().create(EventListService::class.java)
    }

    fun getEvents(p: Int, kw:String?, callback: CallbackEvents){
        service.getEvents(page = p,keyword = kw).enqueue(object : ServiceCallback<ResponseData>(){
            override fun onSuccess(response: ResponseData?) {
                var list = EventList(mutableListOf())
                response?.embedded?.apply {
                   list =  EventList( events.let { e-> EventMapper.map(e).toMutableList() } )
                }
                callback.onSuccess(
                        list
                )
            }

            override fun onError(error: Int, message: String?) {
                callback.onError(error)
            }
        })
    }

    fun getEventDetail(eventId: String, callback: CallbackEventDetail){
        service.getEventDetail(eventId).enqueue(object : ServiceCallback<EventData>(){
            override fun onSuccess(response: EventData?) {
                response?.apply {
                    callback.onSuccess(EventMapper.map(this))
                }
            }

            override fun onError(error: Int, message: String?) {
                callback.onError(error)
            }
        })
    }

    fun getEventsByLocation(page: Int,radius: Int,latitude: Double, longitude:Double, callback: CallbackEvents){
        val location = Location(latitude = latitude.toString(), longitude = longitude.toString())
        val geoPoint = GeoPointMapper.map(location)
        service.getEventsByLocation(page=page,radius = radius,geoPoint = geoPoint).enqueue( object :ServiceCallback<ResponseData>(){
            override fun onSuccess(response: ResponseData?) {
                var list = EventList(mutableListOf())
                response?.embedded?.apply {
                    list = EventList( events.let { e-> EventMapper.map(e).toMutableList() } )
                }
                callback.onSuccess(
                        list
                )
            }

            override fun onError(error: Int, message: String?) {
                callback.onError(error)
            }
        })
    }
}