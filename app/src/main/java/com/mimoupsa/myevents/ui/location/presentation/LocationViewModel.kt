package com.mimoupsa.myevents.ui.location.presentation

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.mimoupsa.myevents.data.local.EventDBRepository
import com.mimoupsa.myevents.data.preferences.PreferencesManager
import com.mimoupsa.myevents.data.remote.callback.CallbackEvents
import com.mimoupsa.myevents.data.remote.datasource.EventsApiDataSource
import com.mimoupsa.myevents.domain.mappers.EventPOJOMapper
import com.mimoupsa.myevents.domain.model.ErrorModel
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.domain.model.EventList
import com.mimoupsa.myevents.ui.event.list.presentation.EventListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val events = MutableLiveData<EventList>()
    val askPermissions = MutableLiveData<Any>()
    val getLocation = MutableLiveData<Any>()
    var isRefreshing = MutableLiveData<Boolean>()
    private val repository = EventDBRepository(application)
    private var insertResult = MutableLiveData<Boolean>()
    var radiusSettings = MutableLiveData<Int>()
    val url = MutableLiveData<String>()
    var error = MutableLiveData<ErrorModel>()

    private var page = 0
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private val dataSource = EventsApiDataSource.INSTANCE

    fun eventsData(): LiveData<EventList> = events

    fun getMoreEvents(){
        isRefreshing.value = true
        val radius = PreferencesManager(getApplication()).radius
        viewModelScope.launch(Dispatchers.IO){
            dataSource.getEventsByLocation(page,radius,latitude,longitude, object : CallbackEvents {
                override fun onSuccess(events: EventList) {
                    isRefreshing.value = false
                    this@LocationViewModel.events.value = events
                }

                override fun onError(errorCode: Int) {
                    isRefreshing.value = false
                    error.value = ErrorModel(EventListViewModel.ERROR_TITLE, EventListViewModel.ERROR_MESSAGE)
                }
            })
        }
    }

    private fun updateEvents(lat: Double, long: Double){
        viewModelScope.launch {
            val radius = PreferencesManager(getApplication()).radius
            dataSource.getEventsByLocation(0,radius,lat,long, object : CallbackEvents{
                override fun onSuccess(events: EventList) {
                    this@LocationViewModel.events.value = events
                }

                override fun onError(errorCode: Int) {
                    error.value = ErrorModel(EventListViewModel.ERROR_TITLE, EventListViewModel.ERROR_MESSAGE)
                }
            })
        }
    }

    fun getInsertResult() = insertResult

    fun checkPermissionResult(result: Boolean){
        if (result) getLocation.postValue(null)
        else askPermissions.postValue(null)
    }

    fun updateRadius(radius: Int){
        PreferencesManager(getApplication()).radius = radius
        updateEvents(latitude,longitude)
    }

    fun setLocation(lat: Double,long: Double){
        latitude = lat
        longitude = long
        updateEvents(lat = lat, long = long)
    }


    fun saveToFavorites(event: Event){
        viewModelScope.launch {
            if (repository.insertEvent(EventPOJOMapper.map(event))){
                insertResult.postValue(true)
            }else insertResult.postValue(false)
        }
    }

    fun onOpenSettingsDialogClicked(){
        radiusSettings.value = PreferencesManager(getApplication()).radius
    }

    fun openUrl(event: Event){
        url.value = event.url
    }

    companion object{
        const val LOCATION_CODE = 44
    }


}