package com.mimoupsa.myevents.ui.event.list.presentation

import android.app.Application
import androidx.lifecycle.*
import com.mimoupsa.myevents.MyEventsApp
import com.mimoupsa.myevents.data.local.EventDBRepository
import com.mimoupsa.myevents.data.remote.callback.CallbackEvents
import com.mimoupsa.myevents.data.remote.datasource.EventsApiDataSource
import com.mimoupsa.myevents.domain.mappers.EventPOJOMapper
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.domain.model.EventList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EventDBRepository(application)
    private val events = MutableLiveData<EventList>()
    private var page = 0
    private var firstCall = true
    private var insertResult = MutableLiveData<Boolean>()

    fun eventsData(): LiveData<EventList> = events


    fun getMoreEvents(){
        if(firstCall){
            firstCall = false
            viewModelScope.launch(Dispatchers.IO){
                EventsApiDataSource.INSTANCE.getEvents(page, object : CallbackEvents{
                    override fun onSuccess(events: EventList) {
                        this@EventListViewModel.events.value = events
                    }

                    override fun onError(errorCode: Int) {
                        // TODO SHOW ERROR
                    }
                })
            }
        }
    }

    fun saveToFavorites(event: Event){
        viewModelScope.launch {
            if (repository.insertEvent(EventPOJOMapper.map(event))){
                insertResult.postValue(true)
            }else insertResult.postValue(false)
        }
    }

    fun getInsertResult() = insertResult
}