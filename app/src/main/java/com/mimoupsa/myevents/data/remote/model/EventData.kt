package com.mimoupsa.myevents.data.remote.model

import com.google.gson.annotations.SerializedName

data class EventData(
        val id: String,
        val images: List<ImageData>,
        val locale: String,
        val name: String,
        val test: Boolean,
        val type: String,
        val url: String,
        val dates: DatesData,
        val classifications: List<ClassificationData>,
        val info: String?,
        val pleaseNote: String?,
        val priceRanges: List<PriceRangesData>?,
        val seatmap: SeatmapData?,
        @SerializedName("_embedded")
        val embedded: EmbeddedEvent

)