package com.mimoupsa.myevents.ui.event.list.presentation

import android.app.Application
import androidx.lifecycle.*
import com.mimoupsa.myevents.MyEventsApp
import com.mimoupsa.myevents.data.local.EventDBRepository
import com.mimoupsa.myevents.data.remote.callback.CallbackEvents
import com.mimoupsa.myevents.data.remote.datasource.EventsApiDataSource
import com.mimoupsa.myevents.domain.mappers.EventPOJOMapper
import com.mimoupsa.myevents.domain.model.ErrorModel
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
    private var keyword: String? = null
    var error = MutableLiveData<ErrorModel>()
    val url = MutableLiveData<String>()

    fun eventsData(): LiveData<EventList> = events

    fun getMoreEvents(){
        if(firstCall){
            firstCall = false
            viewModelScope.launch(Dispatchers.IO){
                EventsApiDataSource.INSTANCE.getEvents(page, keyword, object : CallbackEvents{
                    override fun onSuccess(events: EventList) {
                        this@EventListViewModel.events.value = events
                    }

                    override fun onError(errorCode: Int) {
                        error.value = ErrorModel(ERROR_TITLE, ERROR_MESSAGE)
                    }
                })
            }
        }
    }

    private fun getEvents(){
        viewModelScope.launch(Dispatchers.IO){
            EventsApiDataSource.INSTANCE.getEvents(0, keyword, object : CallbackEvents{
                override fun onSuccess(events: EventList) {
                    this@EventListViewModel.events.value = events
                }

                override fun onError(errorCode: Int) {
                    error.value = ErrorModel(ERROR_TITLE, ERROR_MESSAGE)
                }
            })
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

    fun updateKeyWord(k: String?){
        keyword = k
        getEvents()
    }

    fun openUrl(event: Event){
        url.value = event.url
    }

    companion object{
        const val ERROR_TITLE = "Ha ocurrido un error"
        const val ERROR_MESSAGE = "No se han podido recuperar los eventos, inténtalo de nuevo"
    }
}