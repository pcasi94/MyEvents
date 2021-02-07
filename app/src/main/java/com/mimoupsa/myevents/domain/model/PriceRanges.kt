package com.mimoupsa.myevents.domain.model

import androidx.room.Entity

@Entity
data class PriceRanges(
        val currency: String,
        val max: Double,
        val min: Double,
        val type: String
)