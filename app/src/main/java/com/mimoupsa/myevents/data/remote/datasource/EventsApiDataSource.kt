package com.mimoupsa.myevents.data.remote.datasource

import android.util.Log
import com.mimoupsa.myevents.data.remote.RetrofitHelper
import com.mimoupsa.myevents.data.remote.ServiceCallback
import com.mimoupsa.myevents.data.remote.model.ResponseData
import com.mimoupsa.myevents.data.remote.service.EventListService

class EventsApiDataSource {
    companion object {
        val INSTANCE: EventsApiDataSource = EventsApiDataSource()
    }

    private val service: EventListService by lazy {
        RetrofitHelper.getRetrofit().create(EventListService::class.java)
    }

    fun getEvents(page: Int){
        service.getEvents().enqueue(object : ServiceCallback<ResponseData>(){
            override fun onSuccess(response: ResponseData?) {
                response?.embedded?.events?.first()?.name?.let { Log.i("REMOTE", it) }
            }

            override fun onError(error: Int, message: String?) {
                Log.i("REMOTE",error.toString())
            }
        })
    }
}