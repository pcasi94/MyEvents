package com.mimoupsa.myevents.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseData (
        @SerializedName("_embedded")
        val embedded: EmbeddedData,
        @SerializedName("page")
        val page: PageData
        )


data class EmbeddedData(
        val events: List<Event>
)
data class PageData(
        val size: Int,
        val totalElements: Int,
        val totalPages: Int,
        val number: Int
)