package com.mimoupsa.myevents.ui.event.list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimoupsa.myevents.data.remote.callback.CallbackEvents
import com.mimoupsa.myevents.data.remote.datasource.EventsApiDataSource
import com.mimoupsa.myevents.domain.model.EventList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventListViewModel : ViewModel() {

    private val events = MutableLiveData<EventList>()
    private var page = 0
    private var firstCall = true

    fun eventsData(): LiveData<EventList> = events

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text


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
}