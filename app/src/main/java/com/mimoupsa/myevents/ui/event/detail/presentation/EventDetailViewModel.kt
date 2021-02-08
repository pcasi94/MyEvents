package com.mimoupsa.myevents.ui.event.detail.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mimoupsa.myevents.data.remote.callback.CallbackEventDetail
import com.mimoupsa.myevents.data.remote.datasource.EventsApiDataSource
import com.mimoupsa.myevents.domain.model.ErrorModel
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.ui.event.list.presentation.EventListViewModel
import kotlinx.coroutines.launch

class EventDetailViewModel(application: Application): AndroidViewModel(application) {

    private var firstCall = true
    private val event = MutableLiveData<Event>()

    var error = MutableLiveData<ErrorModel>()
    var openUrl = MutableLiveData<String>()

    fun getEventDetail(id: String){
        if(firstCall){
            firstCall = false
            viewModelScope.launch(viewModelScope.coroutineContext) {
                EventsApiDataSource.INSTANCE.getEventDetail(id, object : CallbackEventDetail {
                    override fun onSuccess(event: Event) {
                        setEvent(event)
                    }

                    override fun onError(errorCode: Int) {
                        error.value = ErrorModel(
                            ERROR_TITLE,
                            ERROR_MESSAGE
                        )
                    }

                })
            }
        }
    }

    fun onEvent(): LiveData<Event> = event
    fun setEvent(e: Event) = run { event.value = e }


    fun onBuyClicked() = run { openUrl.value = event.value?.url }
    fun onFacebookClicked() = run { openUrl.value = event.value?.externalLinks?.facebook?.first()?.url }
    fun onHomePageClicked() = run { openUrl.value = event.value?.externalLinks?.homepage?.first()?.url }
    fun onInstagramClicked() = run { openUrl.value = event.value?.externalLinks?.instagram?.first()?.url }
    fun onSpotifyClicked() = run { openUrl.value = event.value?.externalLinks?.spotify?.first()?.url }
    fun onTwitterClicked() = run { openUrl.value = event.value?.externalLinks?.twitter?.first()?.url }
    fun onYoutubeClicked() = run { openUrl.value = event.value?.externalLinks?.youtube?.first()?.url }

    companion object{
        const val ERROR_TITLE = "Ha ocurrido un error"
        const val ERROR_MESSAGE = "No se han podido recuperar el evento, inténtalo de nuevo"
    }

}