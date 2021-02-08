package com.mimoupsa.myevents.ui.favorite.presentation

import android.app.Application
import androidx.lifecycle.*
import com.mimoupsa.myevents.MyEventsApp
import com.mimoupsa.myevents.data.local.EventDBRepository
import com.mimoupsa.myevents.data.local.db.EventPOJO
import com.mimoupsa.myevents.data.remote.datasource.EventsApiDataSource
import com.mimoupsa.myevents.domain.model.ErrorModel
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.domain.model.EventList
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EventDBRepository(application)
    val onDetail = MutableLiveData<String>()

    val eventDb: LiveData<List<EventPOJO>> = repository.getAllEvents()

    fun deleteFromFavorites(event: EventPOJO){
        viewModelScope.launch {
            repository.deleteEvent(event)
        }
    }

    fun onMoreInfoClicked(event: EventPOJO){
        onDetail.value = event.eventId
    }




}