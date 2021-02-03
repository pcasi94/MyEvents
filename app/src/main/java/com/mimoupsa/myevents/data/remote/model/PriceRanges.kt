package com.mimoupsa.myevents.data.remote.model

data class PriceRanges(
    val currency: String,
    val max: Double,
    val min: Double,
    val type: String
)