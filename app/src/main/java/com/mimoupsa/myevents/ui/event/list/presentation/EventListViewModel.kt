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
import com.mimoupsa.myevents.ui.common.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EventDBRepository(application)
    private val events = MutableLiveData<EventList>()
    private var noMore = false
    private var page = 0
    private var firstCall = true
    private var insertResult = MutableLiveData<Boolean>()
    private var keyword: String? = null
    var error = MutableLiveData<ErrorModel>()
    val onDetail = SingleLiveData<String>()

    fun eventsData(): LiveData<EventList> = events

    fun getMoreEvents(){
        if (!noMore){
            viewModelScope.launch(viewModelScope.coroutineContext){
                EventsApiDataSource.INSTANCE.getEvents(page, keyword, object : CallbackEvents{
                    override fun onSuccess(events: EventList) {
                        if (events.list.isEmpty()) noMore = true
                        page++
                        this@EventListViewModel.events.value?.list?.addAll(events.list)
                        this@EventListViewModel.events.postValue(this@EventListViewModel.events.value)
                    }

                    override fun onError(errorCode: Int) {
                        error.value = ErrorModel(ERROR_TITLE, ERROR_MESSAGE)
                    }
                })

            }
        }
    }

    fun getEvents(){
        if(firstCall){
            firstCall = false
            page = 0
            viewModelScope.launch(viewModelScope.coroutineContext) {
                EventsApiDataSource.INSTANCE.getEvents(page, keyword, object : CallbackEvents {
                    override fun onSuccess(events: EventList) {
                        this@EventListViewModel.events.value = events
                        page++
                    }

                    override fun onError(errorCode: Int) {
                        error.value = ErrorModel(ERROR_TITLE, ERROR_MESSAGE)
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

    fun updateKeyWord(k: String?){
        keyword = k
        firstCall = true
        getEvents()
    }

    fun onMoreInfoClicked(event: Event){
        onDetail.value = event.eventId
    }

    companion object{
        const val ERROR_TITLE = "Ha ocurrido un error"
        const val ERROR_MESSAGE = "No se han podido recuperar los eventos, inténtalo de nuevo"
    }
}