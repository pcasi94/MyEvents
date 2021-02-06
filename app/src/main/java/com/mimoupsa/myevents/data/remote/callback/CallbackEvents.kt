package com.mimoupsa.myevents.data.remote.callback

import com.mimoupsa.myevents.data.remote.model.ResponseData
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.domain.model.EventList

interface CallbackEvents {
    fun onSuccess(events: EventList)
    fun onError(errorCode:Int)
}