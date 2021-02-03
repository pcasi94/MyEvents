package com.mimoupsa.myevents.data.remote.model

import com.google.gson.annotations.SerializedName

data class Event(
    val id: String,
    val images: List<Image>,
    val locale: String,
    val name: String,
    val test: Boolean,
    val type: String,
    val url: String,
    val dates: Dates,
    val classifications: List<Classification>,
    val info: String?,
    val pleaseNote: String?,
    val priceRanges: List<PriceRanges>?,
    val seatmap: Seatmap?,
    @SerializedName("_embedded")
    val embedded: EmbeddedEvent

)