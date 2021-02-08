package com.mimoupsa.myevents.data.remote.callback

import com.mimoupsa.myevents.domain.model.Event

interface CallbackEventDetail {
    fun onSuccess(event: Event)
    fun onError(errorCode:Int)
}