package com.mimoupsa.myevents.data.remote.datasource

import android.util.Log
import com.mimoupsa.myevents.BuildConfig
import com.mimoupsa.myevents.data.remote.RetrofitHelper
import com.mimoupsa.myevents.data.remote.ServiceCallback
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

    fun getEvents(page: Int, callback: CallbackEvents){
        service.getEvents().enqueue(object : ServiceCallback<ResponseData>(){
            override fun onSuccess(response: ResponseData?) {
                response?.apply {
                    callback.onSuccess(
                            EventList( embedded.events.let { e-> EventMapper.map(e) } )
                    )
                }
            }

            override fun onError(error: Int, message: String?) {
                Log.i("REMOTE",error.toString())
            }
        })
    }

    fun getEventDetail(eventId: String){
        service.getEventDetail(eventId).enqueue(object : ServiceCallback<EventData>(){
            override fun onSuccess(response: EventData?) {

            }

            override fun onError(error: Int, message: String?) {

            }
        })
    }

    fun getEventsByLocation(page: Int,latitude: Double, longitude:Double, callback: CallbackEvents){
        val location = Location(latitude = latitude.toString(), longitude = longitude.toString())
        val geoPoint = GeoPointMapper.map(location)
        service.getEventsByLocation(page=page,geoPoint = geoPoint).enqueue( object :ServiceCallback<ResponseData>(){
            override fun onSuccess(response: ResponseData?) {
                response?.apply {
                    callback.onSuccess(EventList( embedded.events.let { e-> EventMapper.map(e) } ))
                }
            }

            override fun onError(error: Int, message: String?) {

            }
        })
    }
}