package com.mimoupsa.myevents.domain.model

data class PriceRanges(
        val currency: String,
        val max: Double,
        val min: Double,
        val type: String
)