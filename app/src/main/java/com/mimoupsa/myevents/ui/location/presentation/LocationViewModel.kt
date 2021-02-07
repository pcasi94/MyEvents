package com.mimoupsa.myevents.ui.location.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimoupsa.myevents.data.remote.callback.CallbackEvents
import com.mimoupsa.myevents.data.remote.datasource.EventsApiDataSource
import com.mimoupsa.myevents.domain.model.EventList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    private val events = MutableLiveData<EventList>()
    val askPermissions = MutableLiveData<Any>()
    val getLocation = MutableLiveData<Any>()
    var isRefreshing = MutableLiveData<Boolean>()

    private var page = 0
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var firstCall = true
    private val dataSource = EventsApiDataSource.INSTANCE

    fun eventsData(): LiveData<EventList> = events

    fun getMoreEvents(){
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO){
            dataSource.getEventsByLocation(page,latitude,longitude, object : CallbackEvents {
                override fun onSuccess(events: EventList) {
                    isRefreshing.value = false
                    this@LocationViewModel.events.value = events
                }

                override fun onError(errorCode: Int) {
                    isRefreshing.value = false
                    // TODO SHOW ERROR
                }
            })
        }
    }

    private fun updateEvents(lat: Double, long: Double){
        viewModelScope.launch {
            dataSource.getEventsByLocation(0,lat,long, object : CallbackEvents{
                override fun onSuccess(events: EventList) {
                    this@LocationViewModel.events.value = events
                }

                override fun onError(errorCode: Int) {
                    // TODO SHOW ERROR
                }
            })
        }
    }

    fun checkPermissionResult(result: Boolean){
        if (result) getLocation.postValue(null)
        else askPermissions.postValue(null)
    }

    fun setLocation(lat: Double,long: Double){
        latitude = lat
        longitude = long
        updateEvents(lat = lat, long = long)
    }


    companion object{
        const val LOCATION_CODE = 44
    }


}