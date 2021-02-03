package com.mimoupsa.myevents.data.remote.callback

import com.mimoupsa.myevents.data.remote.model.ResponseData

interface CallbackEvents {
    fun onSuccess(data: ResponseData)
    fun onError(errorCode:Int)
}